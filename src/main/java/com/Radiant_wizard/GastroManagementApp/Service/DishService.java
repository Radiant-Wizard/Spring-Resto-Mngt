package com.Radiant_wizard.GastroManagementApp.Service;

import com.Radiant_wizard.GastroManagementApp.Entity.Dish;
import com.Radiant_wizard.GastroManagementApp.Repository.DishesDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    DishesDaoImpl dishesDao;

    @Autowired
    public DishService(DishesDaoImpl dishesDao){
        this.dishesDao = dishesDao;
    }

    public List<Dish> getAllDishes(int pageSize, int pageNumber){
        return dishesDao.getAll(pageSize, pageNumber);
    }
}
