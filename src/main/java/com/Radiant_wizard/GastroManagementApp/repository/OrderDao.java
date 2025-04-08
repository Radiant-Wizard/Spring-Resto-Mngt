package com.Radiant_wizard.GastroManagementApp.repository;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    List<Order> getAll(int pageSize, int pageNumber);
    Order getByReference(String reference);
    Order getByID(long orderId);
    void createOrder(Order order) throws SQLException;
    void save(Order order) throws SQLException;
    void updateStatus(StatusType statusType, long orderId);
}
