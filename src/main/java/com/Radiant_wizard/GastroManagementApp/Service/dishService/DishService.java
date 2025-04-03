package com.Radiant_wizard.GastroManagementApp.Service.dishService;

import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;
import com.Radiant_wizard.GastroManagementApp.dao.DishesDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    List<Dish> getAllDishes(int pageSize, int pageNumber);
}

