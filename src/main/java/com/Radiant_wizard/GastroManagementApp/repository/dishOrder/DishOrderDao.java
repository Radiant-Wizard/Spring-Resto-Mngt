package com.Radiant_wizard.GastroManagementApp.repository.dishOrder;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DishOrderDao {

    long findDishOrderIdByDish(long id);
    void updateDishOrderStatus(StatusType statusType, long orderId);

    void addDishOrder(DishOrder dishOrder, long orderId);

    List<DishOrder> getDishOrdersByOrderId(long orderId);

    void deleteDishOrder(long dishOrderId);

    Map<Long, DishOrder> dishOrderByDishId(long dishId, LocalDateTime start, LocalDateTime end);
}

