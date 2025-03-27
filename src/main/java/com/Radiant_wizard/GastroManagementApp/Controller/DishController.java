package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Entity.Dish;
import com.Radiant_wizard.GastroManagementApp.Service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;


    public DishController(DishService dishService){
        this.dishService = dishService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Dish>> getPing(@RequestParam int pageSize, int pageNumber){
        List<Dish> dishes;
        try {
            dishes = dishService.getAllDishes(pageSize, pageNumber);
            return ResponseEntity.ok(dishes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
