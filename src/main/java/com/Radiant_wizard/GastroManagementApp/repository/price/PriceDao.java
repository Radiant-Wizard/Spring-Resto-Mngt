package com.Radiant_wizard.GastroManagementApp.repository.price;

import com.Radiant_wizard.GastroManagementApp.entity.model.Price;

import java.util.List;

public interface PriceDao {
    List<Price> getPriceByIngredientID(long ingredientId);
    void save(List<Price> prices, long ingredientId);
}
