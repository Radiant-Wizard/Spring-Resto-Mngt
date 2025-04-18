package com.Radiant_wizard.GastroManagementApp.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Dish {
    private final Long dishId;
    private final String dishName;
    private final Integer price;
    @JsonIgnore
    private final List<Ingredient> ingredients;

    public Dish(Long dishId, String dishName, Integer price, List<Ingredient> ingredients) throws IllegalAccessException {
        if (ingredients == null) {
            throw new IllegalAccessException();
        }

        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.ingredients = ingredients;
    }


    public double getTotalCostIngredient(LocalDateTime dateTime) {
        List<Ingredient> ingredients = this.ingredients;
        LocalDateTime now = LocalDateTime.now();
        double cost = 0;


        for (Ingredient ingredient : ingredients) {
            Double nearestValue = ingredient.getNearestPrice(dateTime).getValue();
            cost += (ingredient.getNeededQuantity() * nearestValue);
        }
        return cost;
    }

    public double getGrossMargin(LocalDateTime localDateTime) {
        double totalProductionCost = getTotalCostIngredient(localDateTime);
        double salePrice = this.price;

        return totalProductionCost - salePrice;
    }

    public Double getAvailableQuantity(LocalDateTime localDateTime) {
        return this.ingredients
                .stream()
                .mapToDouble(ingredient -> (Math.round(ingredient.getAvailableQuantity(localDateTime) / ingredient.getNeededQuantity())))
                .min()
                .orElse(0);
    }

    public Double getAvailableQuantity() {
        return this.ingredients
                .stream()
                .mapToDouble(ingredient -> (Math.round(ingredient.getAvailableQuantity(LocalDateTime.now()) / ingredient.getNeededQuantity())))
                .min()
                .orElse(0);
    }

    @Override
    public String toString() {
        return "Dish {\n" +
                "  dishId      : " + dishId + ",\n" +
                "  dishName    : '" + dishName + "',\n" +
                "  price       : " + price + ",\n" +
                "}";
    }
}
