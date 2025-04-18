package com.Radiant_wizard.GastroManagementApp.Service.Order;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.OrderDish;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.order.OrderDto;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    List<Order> getAll(Integer pageSize, Integer pageNumber);
    OrderDto getOrderByRef(String reference);
    OrderDto addDishesToOrder(List<OrderDish> dishOrders, String reference) throws SQLException;
}
