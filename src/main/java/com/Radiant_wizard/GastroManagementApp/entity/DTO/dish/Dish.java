package com.Radiant_wizard.GastroManagementApp.entity.DTO.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dish {
    private long id;
    private String name;
    private Double availableQuantity;
    private Double actualPrice;
    private List<DishIngredient> ingredients;
}
