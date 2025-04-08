package com.Radiant_wizard.GastroManagementApp.repository;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;

import java.util.List;

public interface DishOrderDao {

    void updateDishOrderStatus(StatusType statusType, long orderId);

    void addDishOrder(DishOrder dishOrder, long orderId);

    List<DishOrder> getDishOrdersByOrderId(long orderId);

    void deleteDishOrder(long dishOrderId);
}

