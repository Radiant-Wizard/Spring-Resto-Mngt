package com.Radiant_wizard.GastroManagementApp.entity.DTO.dish;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.IngredientBasicProperty;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishIngredient {
    private Double requireQuantity;
    private Unit unit;
    private IngredientBasicProperty ingredient;
}
