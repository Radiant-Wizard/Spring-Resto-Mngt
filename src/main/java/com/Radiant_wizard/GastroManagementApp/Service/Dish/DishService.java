package com.Radiant_wizard.GastroManagementApp.Service.Dish;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    List<Dish> getAllDishes(int pageSize, int pageNumber);

}

