package com.Radiant_wizard.GastroManagementApp.Service.ingredientService;

import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.model.IngredientResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface IngredientService {
    List<IngredientResponseEntity> getIngredientsByCriteria(List<Criteria> criteriaList, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException;
    Ingredient getIngredientById(long ingredientID);
}
