package com.Radiant_wizard.GastroManagementApp.dao;

import com.Radiant_wizard.GastroManagementApp.dao.repository.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.LogicalOperator;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public class IngredientDaoImpl implements  IngredientDao{
    private final Datasource datasource;

    @Autowired
    private PriceDaoImpl priceDao;

    @Autowired
    private StockMovementDaoImpl stockMovementDao;

    public IngredientDaoImpl(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<Ingredient> getIngredientByCriteria(List<Criteria> criteriaList, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();

        String query =
                "SELECT ingredient_id, ingredient_name, last_modification, unit_price, unit from ingredients where 1=1";

        for (Criteria criteria : criteriaList) {
            String columnName = criteria.getColumnName();
            Object columnValue = criteria.getColumnValue();
            String operator = criteria.getOperator();
            LogicalOperator logicalOperator = criteria.getLogicalOperator();

            query += " " + logicalOperator.toString() + " " ;
            if ("BETWEEN".equalsIgnoreCase(operator)){
                Object secondValue = criteria.getSecondValue();
                query += String.format(" %s BETWEEN '%s' AND '%s' ", columnName, columnValue, secondValue);
            }else if("LIKE".equalsIgnoreCase(operator)){
                query += columnName + " ILIKE '%"+ columnValue + "%' ";
            } else {
                query += String.format(" %s %s %s ", columnName, operator, columnValue);
            }
        }

        if (orderBy != null && !orderBy.isEmpty()) {
            query += " ORDER BY " + orderBy + (ascending ? " ASC " : " DESC ");
        }
        if (pageSize != null && pageNumber != null) {
            int offset = pageSize * (pageNumber - 1);
            query += " LIMIT " + pageSize + " OFFSET " + offset;
        }

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                long ingredientId = resultSet.getLong("ingredient_id");

                ingredients.add(new Ingredient(
                        resultSet.getLong("ingredient_id"),
                        resultSet.getString("ingredient_name"),
                        resultSet.getObject("last_modification", LocalDateTime.class),
                        Unit.valueOf(resultSet.getString("unit")),
                        priceDao.getPriceByIngredientID(ingredientId),
                        stockMovementDao.getStockByIngredientId(ingredientId)
                ));
            }
        }
        return ingredients;
    }

    @Override
    public Ingredient getIngredientById(long id) {
        String query = "SELECT ingredient_id, ingredient_name, last_modification, unit_price, unit from ingredients where ingredient_id = ? ";
        Ingredient ingredient = null;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredient = new Ingredient(
                        resultSet.getLong("ingredient_id"),
                        resultSet.getString("ingredient_name"),
                        resultSet.getObject("last_modification", LocalDateTime.class),
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

}
