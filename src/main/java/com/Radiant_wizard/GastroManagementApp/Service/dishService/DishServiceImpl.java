package com.Radiant_wizard.GastroManagementApp.Service.dishService;

import com.Radiant_wizard.GastroManagementApp.repository.DishesDaoImpl;
import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DishServiceImpl implements DishService {
    DishesDaoImpl dishesDao;

    @Autowired
    public DishServiceImpl(DishesDaoImpl dishesDao){
        this.dishesDao = dishesDao;
    }

    @Override
    public List<Dish> getAllDishes(int pageSize, int pageNumber){
        return dishesDao.getAll(pageSize, pageNumber);
    }
}
