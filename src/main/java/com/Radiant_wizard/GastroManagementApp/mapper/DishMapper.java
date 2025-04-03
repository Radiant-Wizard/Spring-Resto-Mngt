package com.Radiant_wizard.GastroManagementApp.mapper;

import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishMapper {
    public List<Dish> resultSetToDish(ResultSet resultSet, List<Ingredient> ingredients) throws SQLException, IllegalAccessException {
        List<Dish> dishes = new ArrayList<>();

        while (resultSet.next()) {
            dishes.add(new Dish(
                    resultSet.getLong("dish_id"),
                    resultSet.getString("dish_name"),
                    resultSet.getInt("dish_price"),
                    ingredients
            ));
        }
        return dishes;
    }

}
