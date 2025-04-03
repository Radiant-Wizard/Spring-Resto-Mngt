package com.Radiant_wizard.GastroManagementApp.dao;

import com.Radiant_wizard.GastroManagementApp.entity.model.Price;

import java.util.List;

public interface PriceDao {
    List<Price> getPriceByIngredientID(long ingredientId);
}
