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
//            System.out.println(ingredient.getIngredientName() + " : " + ingredient.getNeededQuantity() + " : " + (ingredient.getNeededQuantity() * nearestValue));
        }
        return cost;
    }

    public double getGrossMargin(LocalDateTime localDateTime) {
        double totalProductionCost = getTotalCostIngredient(localDateTime);
        double salePrice = this.price;

        return totalProductionCost - salePrice;
    }

    public Double getAvailableQuantity(LocalDateTime localDateTime) {
//        double smallestAvailableDishQuantity = Double.MAX_VALUE;
//        for (Ingredient ingredient : this.ingredients) {
//            double availableDishMade = Math.round(ingredient.getAvailableQuantity(usedDate) / ingredient.getNeededQuantity());
//            if (availableDishMade < smallestAvailableDishQuantity) {
//                smallestAvailableDishQuantity = availableDishMade;
//            }
//        }
        return this.ingredients
                .stream()
                .mapToDouble(ingredient -> (Math.round(ingredient.getAvailableQuantity(localDateTime) / ingredient.getNeededQuantity())))
                .min()
                .orElse(0);
    }

    public Double getAvailableQuantity() {
        return this.ingredients
                .stream()
                .mapToDouble(ingredient -> (Math.round(ingredient.getAvailableQuantity() / ingredient.getNeededQuantity())))
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
