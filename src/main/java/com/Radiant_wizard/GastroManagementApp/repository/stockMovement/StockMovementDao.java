package com.Radiant_wizard.GastroManagementApp.repository.stockMovement;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.stockMovement.StockMovementDto;
import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;

import java.util.List;

public interface StockMovementDao {
    List<StockMovement> getStockByIngredientId(long ingredientId);
    void save(List<StockMovementDto> stockMovements, long ingredientId);
}
