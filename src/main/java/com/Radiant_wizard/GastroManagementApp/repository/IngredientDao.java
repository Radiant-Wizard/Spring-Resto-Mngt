package com.Radiant_wizard.GastroManagementApp.repository;

import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;

import java.sql.SQLException;
import java.util.List;

public interface IngredientDao {
    List<Ingredient> getIngredientByCriteria(List<Criteria> criteriaList, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException;
    Ingredient getIngredientById(long id);
    void save(List<Ingredient> ingredients);
}
