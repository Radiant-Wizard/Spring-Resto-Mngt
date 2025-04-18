package com.Radiant_wizard.GastroManagementApp.repository.dish;

import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;

import java.sql.SQLException;
import java.util.List;

public interface DishesDao {
    List<Dish> getAll(int pageSize, int pageNumber);
    List<Dish> getDishesByCriteria(List<Criteria> criteriaList, String orderBy, Boolean ascending, Integer pageSize, Integer pageNumber) throws SQLException;
    Dish getDishesById(long dishId) throws SQLException, IllegalAccessException;
    void saveDishes(List<Dish> dish) throws SQLException;
    void deleteDish(long dishId);
}
