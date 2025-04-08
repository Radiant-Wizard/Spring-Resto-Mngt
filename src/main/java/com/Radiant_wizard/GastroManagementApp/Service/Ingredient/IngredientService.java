package com.Radiant_wizard.GastroManagementApp.Service.Ingredient;

import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.Ingredient;

import java.sql.SQLException;
import java.util.List;

public interface IngredientService {
    List<Ingredient> getIngredientsByCriteria(List<Criteria> criteriaList, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException;
    com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient getIngredientById(long ingredientID);
}
