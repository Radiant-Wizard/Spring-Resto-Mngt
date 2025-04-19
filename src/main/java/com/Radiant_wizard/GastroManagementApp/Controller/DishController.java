package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Service.Dish.DishService;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Mode;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/dishes")
public class DishController {
    @Autowired
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/")
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

    @PostMapping("/")
    public ResponseEntity<Object> newDishes(@RequestBody List<com.Radiant_wizard.GastroManagementApp.entity.model.Dish> dishes){
        try {
            dishService.saveDishes(dishes);
            return ResponseEntity.ok("The dishes has been saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("/{id}/processingTime")
    public ResponseEntity<Object> getProcessingTime(@PathVariable("id") long dishId,
                                                    @RequestParam LocalDateTime start,
                                                    @RequestParam LocalDateTime end,
                                                    @RequestParam (defaultValue = "SECONDS")TimeUnit unit,
                                                    @RequestParam (defaultValue = "AVG") Mode mode
                                                    ){
        try {
            double processingTime = dishService.calculateProcessingTimeForDishOrders(dishId, start, end, unit, mode);
            return ResponseEntity.ok(mode + " PROCESSING TIME : " + processingTime);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("/{id}/dishOrders")
    public ResponseEntity<Object> getDishOrderByDishId(@PathVariable("id") long dishId,
                                                       @RequestParam LocalDateTime start,
                                                       @RequestParam LocalDateTime end
    ){
        try {
            Map<Long, DishOrder> dishOrder = dishService.dishOrderByDishId(dishId, start, end);
            return ResponseEntity.ok(dishOrder);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
