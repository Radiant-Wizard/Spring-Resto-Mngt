package com.Radiant_wizard.GastroManagementApp.repository;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;

import java.sql.SQLException;
import java.util.List;

public interface IngredientDao {
    List<com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient> getIngredientByCriteria(List<Criteria> criteriaList, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException;
    com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient getIngredientById(long id);
    com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient getIngredientByName(String name);
    void save(List<Ingredient> ingredients);
}
