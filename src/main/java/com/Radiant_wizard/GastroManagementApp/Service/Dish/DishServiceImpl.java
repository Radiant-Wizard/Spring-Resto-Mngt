package com.Radiant_wizard.GastroManagementApp.Service.Dish;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish;
import com.Radiant_wizard.GastroManagementApp.mapper.DishMapper;
import com.Radiant_wizard.GastroManagementApp.repository.DishesDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DishServiceImpl implements DishService {
    private final DishesDaoImpl dishesDao;

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    public DishServiceImpl(DishesDaoImpl dishesDao, DishMapper dishMapper){
        this.dishesDao = dishesDao;
        this.dishMapper = dishMapper;
    }

    @Override
    public List<Dish> getAllDishes(int pageSize, int pageNumber){
        return dishesDao.getAll(pageSize, pageNumber).stream().map(dish -> dishMapper.dishDbToDish(dish)).toList();
    }
}
