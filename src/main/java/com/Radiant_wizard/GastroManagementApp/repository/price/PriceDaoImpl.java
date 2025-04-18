package com.Radiant_wizard.GastroManagementApp.repository.price;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.model.Price;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PriceDaoImpl implements PriceDao {
    private final Datasource datasource;

    public PriceDaoImpl(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<Price> getPriceByIngredientID(long ingredientId) {
        List<Price> priceList = new ArrayList<>();
        String sql = "SELECT ingredient_id, last_modification, unit_price from prices where ingredient_id = ?";

        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, ingredientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    priceList.add(new Price(
                            resultSet.getObject("last_modification", LocalDateTime.class),
                            resultSet.getDouble("unit_price")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return priceList;
    }

    @Override
    public void save(List<Price> prices, long ingredientId) {
        String sql = "INSERT INTO prices (ingredient_id, last_modification, unit_price) VALUES(?, ?::TIMESTAMP, ?)";
        try (Connection connection = datasource.getConnection()) {
            prices.forEach(price -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setLong(1, ingredientId);
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(price.getModificationDate()));
                    preparedStatement.setDouble(3, price.getValue());
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
