package com.Radiant_wizard.GastroManagementApp.Service.Ingredient;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.stockMovement.StockMovementDto;
import com.Radiant_wizard.GastroManagementApp.entity.model.Price;
import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;

import java.sql.SQLException;
import java.util.List;

public interface IngredientService {
    List<Ingredient> getIngredientsByCriteria(Double priceMin, Double priceMax, String ingredientName, Double unitPrice,
                                              String operator, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException;
    Ingredient getIngredientById(long ingredientID) throws NoSuchFieldException;
    Ingredient getIngredientByName(String name) throws NoSuchFieldException;
    Ingredient updatePrice(List<Price> prices, long ingredientId);
    void addStockMovement(List<StockMovementDto> stockMovements, long ingredientId);
}
