package com.Radiant_wizard.GastroManagementApp.repository;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.LogicalOperator;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public class IngredientDaoImpl implements  IngredientDao{
    private final Datasource datasource;

    @Autowired
    private final PriceDaoImpl priceDao;

    @Autowired
    private final StockMovementDaoImpl stockMovementDao;

    public IngredientDaoImpl(Datasource datasource, PriceDaoImpl priceDao, StockMovementDaoImpl stockMovementDao) {
        this.datasource = datasource;
        this.priceDao = priceDao;
        this.stockMovementDao = stockMovementDao;
    }

    @Override
    public List<com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient> getIngredientByCriteria(List<Criteria> criteriaList, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException {
        List<com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient> ingredients = new ArrayList<>();

        StringBuilder query =
                new StringBuilder("SELECT ingredient_id, ingredient_name, unit from ingredients where 1=1");

        for (Criteria criteria : criteriaList) {
            String columnName = criteria.getColumnName();
            Object columnValue = criteria.getColumnValue();
            String operator = criteria.getOperator();
            LogicalOperator logicalOperator = criteria.getLogicalOperator();

            query.append(" ").append(logicalOperator.toString()).append(" ");
            if ("BETWEEN".equalsIgnoreCase(operator)){
                Object secondValue = criteria.getSecondValue();
                query.append(String.format(" %s BETWEEN '%s' AND '%s' ", columnName, columnValue, secondValue));
            }else if("LIKE".equalsIgnoreCase(operator)){
                query.append(columnName).append(" ILIKE '%").append(columnValue).append("%' ");
            } else {
                query.append(String.format(" %s %s %s ", columnName, operator, columnValue));
            }
        }

        if (orderBy != null && !orderBy.isEmpty()) {
            query.append(" ORDER BY ").append(orderBy).append(ascending ? " ASC " : " DESC ");
        }
        if (pageSize != null && pageNumber != null) {
            int offset = pageSize * (pageNumber - 1);
            query.append(" LIMIT ").append(pageSize).append(" OFFSET ").append(offset);
        }

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString());
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                long ingredientId = resultSet.getLong("ingredient_id");

                ingredients.add(new com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient(
                        resultSet.getLong("ingredient_id"),
                        resultSet.getString("ingredient_name"),
                        Unit.valueOf(resultSet.getString("unit")),
                        priceDao.getPriceByIngredientID(ingredientId),
                        stockMovementDao.getStockByIngredientId(ingredientId)
                ));

            }
        }
        return ingredients;
    }

    @Override
    public com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient getIngredientById(long id) {
        String query = "SELECT ingredient_id, ingredient_name, unit from ingredients where ingredient_id = ? ";
        com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient ingredient = null;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredient = new com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient(
                        resultSet.getLong("ingredient_id"),
                        resultSet.getString("ingredient_name"),
                        Unit.valueOf(resultSet.getString("unit")),
                        priceDao.getPriceByIngredientID(id),
                        stockMovementDao.getStockByIngredientId(id)
                );
            }
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return ingredient;
    }

    @Override
    public com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient getIngredientByName(String name) {
        String query =
                "SELECT ingredient_id, ingredient_name, unit from ingredients where ingredient_name = ? ";
        com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient ingredient = null;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredient = new com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient(
                        resultSet.getLong("ingredient_id"),
                        resultSet.getString("ingredient_name"),
                        Unit.valueOf(resultSet.getString("unit")),
                        priceDao.getPriceByIngredientID(resultSet.getLong("ingredient_id")),
                        stockMovementDao.getStockByIngredientId(resultSet.getLong("ingredient_id"))
                );
            }
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        System.out.println(ingredient);
        return ingredient;
    }

    @Override
    public void save(List<Ingredient> ingredients) {
        String sql = "INSERT INTO ingredients (ingredient_id, ingredient_name, last_modification, unit_price, unit) VALUES(?, ?, ?::timestamp, ?, ?::measurement_unit)";

        try (Connection connection = datasource.getConnection()){
            ingredients.forEach(ingredient -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setLong(1, ingredient.getId());
                    preparedStatement.setString(2, ingredient.getName());
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                    preparedStatement.setDouble(4, ingredient.getActualPrice());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
