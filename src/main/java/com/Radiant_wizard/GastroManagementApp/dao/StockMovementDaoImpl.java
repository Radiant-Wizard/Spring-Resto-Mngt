package com.Radiant_wizard.GastroManagementApp.dao;

import com.Radiant_wizard.GastroManagementApp.dao.repository.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.MovementType;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementDaoImpl implements StockMovementDao{
    private final Datasource datasource;

    public StockMovementDaoImpl(Datasource datasource){
        this.datasource = datasource;
    }

    @Override
    public List<StockMovement> getStockByIngredientId(long ingredientId) {
        List<StockMovement> stockList = new ArrayList<>();
        String sql = "SELECT ingredient_id, movement_date, movement_type, quantity, unit from stock_movement where ingredient_id = ?";

        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, ingredientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    stockList.add(new StockMovement(
                            ingredientId,
                            resultSet.getDouble("quantity"),
                            Unit.valueOf(resultSet.getString("unit")),
                            MovementType.valueOf(resultSet.getString("movement_type")),
                            resultSet.getObject("movement_date", LocalDateTime.class)
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockList;
    }
}
