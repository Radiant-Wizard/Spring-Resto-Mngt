package com.Radiant_wizard.GastroManagementApp.mapper;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.DishIngredient;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.OrderDish;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.IngredientBasicProperty;
import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
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

    public com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish dishDbToDish(Dish dish) {
        com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish dishResponse = new com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish();
        dishResponse.setId(dish.getDishId());
        dishResponse.setName(dish.getDishName());
        dishResponse.setIngredients(dish.getIngredients().stream()
                .map
                        (ingredient ->
                                new DishIngredient(
                                        ingredient.getNeededQuantity(),
                                        ingredient.getUnit(),
                                        new IngredientBasicProperty(
                                                ingredient.getIngredientId(),
                                                ingredient.getIngredientName()
                                        ),
                                        ingredient.getAvailableQuantity(LocalDateTime.now())
                                )).toList());
        dishResponse.setActualPrice(Double.valueOf(dish.getPrice()));
        dishResponse.setAvailableQuantity(dish.getAvailableQuantity());
        return dishResponse;
    }

    public OrderDish mapToOrderDish(DishOrder dishOrder) {
        return new OrderDish(
                dishOrder.getCommendedDish().getDishId(),
                dishOrder.getCommendedDish().getDishName(),
                dishOrder.getDishQuantityCommanded(),
                dishOrder.getActualStatus()
        );
    }

    public OrderDish mapToNoStatusOrderDish(DishOrder dishOrder) {
        return new OrderDish(
                dishOrder.getCommendedDish().getDishId(),
                dishOrder.getDishQuantityCommanded()
        );
    }

}
