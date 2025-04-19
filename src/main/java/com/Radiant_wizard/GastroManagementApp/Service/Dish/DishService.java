package com.Radiant_wizard.GastroManagementApp.Service.Dish;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Mode;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public interface DishService {
    List<Dish> getAllDishes(int pageSize, int pageNumber);
    double calculateProcessingTimeForDishOrders(long dishId, LocalDateTime start, LocalDateTime end, TimeUnit unit, Mode mode);
    Map<Long, DishOrder> dishOrderByDishId(long dishId, LocalDateTime start, LocalDateTime end);
}

