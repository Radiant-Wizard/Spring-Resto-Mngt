package com.Radiant_wizard.GastroManagementApp.dao;

import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;

import java.util.List;

public interface StockMovementDao {
    List<StockMovement> getStockByIngredientId(long ingredientId);
}
