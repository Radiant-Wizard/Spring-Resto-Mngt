package com.Radiant_wizard.GastroManagementApp.repository.sale;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.model.Sale;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SaleDaoImpl implements SaleDao{
    private final Datasource datasource;

    public SaleDaoImpl(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<Sale> getBestSale(int top, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = """
                SELECT
                    d.dish_name,
                    d.dish_price AS unit_price,
                    SUM(od.ordered_dish_quantity) AS total_quantity_ordered,
                    SUM(d.dish_price * od.ordered_dish_quantity) AS total_amount
                FROM
                    order_dish od
                JOIN
                    order_dish_status ods ON ods.order_dish_id = od.order_dish_id
                JOIN
                    dishes d ON d.dish_id = od.dish_id
                WHERE
                    ods.order_dish_status = 'FINISHED'
                    AND ods.order_dish_creation_date BETWEEN ?::TIMESTAMP AND ?::TIMESTAMP
                GROUP BY
                    d.dish_name, d.dish_price
                ORDER BY
                    total_quantity_ordered DESC
                LIMIT ?
                """;
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endTime));
            preparedStatement.setInt(3, top);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    sales.add(new Sale(
                            resultSet.getString("dish_name"),
                            resultSet.getDouble("total_quantity_ordered"),
                            resultSet.getDouble("total_amount")
                    ));
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return sales;
    }
}
