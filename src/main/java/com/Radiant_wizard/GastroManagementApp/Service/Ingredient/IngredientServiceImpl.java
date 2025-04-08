package com.Radiant_wizard.GastroManagementApp.Service.Ingredient;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.LogicalOperator;
import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.model.Price;
import com.Radiant_wizard.GastroManagementApp.mapper.IngredientMapper;
import com.Radiant_wizard.GastroManagementApp.repository.IngredientDaoImpl;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.Ingredient;
import com.Radiant_wizard.GastroManagementApp.repository.PriceDaoImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl {
    private final IngredientDaoImpl ingredientDao;
    private final PriceDaoImpl priceDao;
    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(IngredientDaoImpl ingredientDao, PriceDaoImpl priceDao, IngredientMapper ingredientMapper) {
        this.ingredientDao = ingredientDao;
        this.priceDao = priceDao;
        this.ingredientMapper = ingredientMapper;
    }

    public List<Ingredient> getIngredientsByCriteria(Double priceMin, Double priceMax, String ingredientName, Double unitPrice,
                                                     String operator, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException {


        List<Criteria> criteriaList = new ArrayList<>();
        if (priceMax != null && priceMin != null) {
            criteriaList.add(new Criteria("unit_price", priceMin, priceMax, "BETWEEN", LogicalOperator.AND));
        } else {
            if (priceMin != null) {
                criteriaList.add(new Criteria("unit_price", priceMin, ">=", LogicalOperator.AND));
            }
            if (priceMin != null && priceMin < 0) {
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
        List<com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient> ingredientList = ingredientDao.getIngredientByCriteria(criteriaList, orderBy, ascending, pageSize, pageNumber);
        return ingredientList
                .stream()
                .map(ingredientMapper::ingredientToIngredientDto).collect(Collectors.toList());
    }

    public Ingredient getIngredientById(long ingredientID) throws NoSuchFieldException {
        com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient ingredient = ingredientDao.getIngredientById(ingredientID);
        if (ingredient == null) {
            throw new NoSuchFieldException("The ingredient hasn't been found!");
        }
        return ingredientMapper.ingredientToIngredientDto(ingredient);
    }

    public Ingredient getIngredientByName(String name) throws NoSuchFieldException {
        com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient ingredient = ingredientDao.getIngredientByName(name);
        System.out.println(name + " getIngredientByName name");
        if (ingredient == null) {
            throw new NoSuchFieldException("The ingredient hasn't been found!");
        }
        return ingredientMapper.ingredientToIngredientDto(ingredient);
    }

    public Ingredient updatePrice(List<Price> prices, long ingredientId) {
        try {
            com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient
                    ingredient = ingredientDao.getIngredientById(ingredientId);
            priceDao.save(prices, ingredientId);
            return getIngredientByName(ingredient.getIngredientName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
