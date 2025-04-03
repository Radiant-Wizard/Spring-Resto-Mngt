package com.Radiant_wizard.GastroManagementApp.Service.ingredientService;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.LogicalOperator;
import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;
import com.Radiant_wizard.GastroManagementApp.dao.IngredientDaoImpl;
import com.Radiant_wizard.GastroManagementApp.entity.model.IngredientResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl {
    IngredientDaoImpl ingredientDao;

    public IngredientServiceImpl(IngredientDaoImpl ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public List<IngredientResponseEntity> getIngredientsByCriteria(Double priceMin, Double priceMax, String ingredientName, Double unitPrice,
                                                     String operator, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException {


        List<Criteria> criteriaList = new ArrayList<>();
        if (priceMax != null && priceMin != null) {
            criteriaList.add(new Criteria("unit_price", priceMin, priceMax, "BETWEEN", LogicalOperator.AND));
        } else {
            if (priceMin != null) {
                criteriaList.add(new Criteria("unit_price", priceMin, ">=", LogicalOperator.AND));
            }
            if (priceMin != null && priceMin < 0){
                throw new IllegalArgumentException("The minimum price should not be negative : " + priceMin);
            }
            if (priceMax != null) {
                criteriaList.add(new Criteria("unit_price", priceMax, "<=", LogicalOperator.AND));
            }
        }
        if (ingredientName != null) {
            criteriaList.add(new Criteria("ingredient_name", ingredientName, "LIKE", LogicalOperator.AND));
        }
        if (unitPrice != null && operator != null) {
            criteriaList.add(new Criteria("unit_price", unitPrice, operator, LogicalOperator.AND));
        }
        List<Ingredient> ingredientList = ingredientDao.getIngredientByCriteria(criteriaList, orderBy, ascending, pageSize, pageNumber);
        return ingredientList
                .stream()
                .map(ingredient -> {
                    IngredientResponseEntity ingredientResponseEntity = new IngredientResponseEntity();
                    ingredientResponseEntity.setId(ingredient.getIngredientId());
                    ingredientResponseEntity.setName(ingredient.getIngredientName());
                    ingredientResponseEntity.setUnitPrice(ingredient.getNearestPrice().getValue());
                    ingredientResponseEntity.setUpdatedAt(ingredient.getLastModification());
                    return ingredientResponseEntity;
                }).collect(Collectors.toList());
    }

    public IngredientResponseEntity getIngredientById(long ingredientID) throws NoSuchFieldException {
        Ingredient ingredient = ingredientDao.getIngredientById(ingredientID);
        if (ingredient == null ){
            throw new NoSuchFieldException("The ingredient hasn't been found!");
        }
        IngredientResponseEntity ingredientResponseEntity = new IngredientResponseEntity();
        ingredientResponseEntity.setId(ingredient.getIngredientId());
        ingredientResponseEntity.setName(ingredient.getIngredientName());
        ingredientResponseEntity.setUnitPrice(ingredient.getNearestPrice().getValue());
        ingredientResponseEntity.setUpdatedAt(ingredient.getLastModification());
        return ingredientResponseEntity;
    }
}
