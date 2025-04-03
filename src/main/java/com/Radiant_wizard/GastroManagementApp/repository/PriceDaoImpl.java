package com.Radiant_wizard.GastroManagementApp.repository;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.model.Price;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PriceDaoImpl implements PriceDao{
    private final Datasource datasource;

    public PriceDaoImpl(Datasource datasource){
        this.datasource = datasource;
    }
    @Override
    public List<Price> getPriceByIngredientID(long ingredientId) {
        List<Price> priceList = new ArrayList<>();
        String sql = "SELECT ingredient_id, last_modification, unit_price from ingredients where ingredient_id = ?";

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
}
