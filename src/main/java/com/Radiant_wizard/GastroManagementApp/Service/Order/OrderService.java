package com.Radiant_wizard.GastroManagementApp.Service.Order;

import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll(Integer pageSize, Integer pageNumber);
    Order getOrderByRef(String reference);
    void addDishToOrder(Dish dish, Double quantity);
}
