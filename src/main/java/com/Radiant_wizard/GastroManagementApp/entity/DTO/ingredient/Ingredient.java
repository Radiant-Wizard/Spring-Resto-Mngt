package com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient;

import com.Radiant_wizard.GastroManagementApp.entity.model.Price;
import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ingredient {
    private long id;
    private String name;
    private Double availableQuantity;
    private Double actualPrice;
    private List<StockMovement> stockMovements;
    private List<Price> prices;
}

