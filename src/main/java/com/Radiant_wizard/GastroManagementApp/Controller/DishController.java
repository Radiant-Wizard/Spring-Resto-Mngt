package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Service.Dish.DishService;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish>> getPing(@RequestParam(required = false, defaultValue = "10") int pageSize
            , @RequestParam(required = false, defaultValue = "1") int pageNumber) {
        List<Dish> dishes;
        try {
            dishes = dishService.getAllDishes(pageSize, pageNumber);
            return ResponseEntity.ok(dishes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
