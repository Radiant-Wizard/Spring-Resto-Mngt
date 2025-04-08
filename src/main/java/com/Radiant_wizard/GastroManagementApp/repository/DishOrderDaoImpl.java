package com.Radiant_wizard.GastroManagementApp.repository;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishOrderDaoImpl implements DishOrderDao{
    @Autowired
    Datasource datasource;
    @Autowired
    DishesDaoImpl dishesDao;
    @Autowired
    StatusDaoImpl statusDao;
    @Autowired
    OrderDaoImpl orderDao;

    public DishOrderDaoImpl(Datasource datasource){
        this.datasource = datasource;
    }


    private long findOrderId(long orderDishId) {
        String sql = "SELECT (order_dish_id, dish_id, order_id, ordered_dish_quantity) from order_dish where order_dish_id = ?";
        long orderId = 0;

        try (Connection connection = datasource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, orderDishId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderId = resultSet.getLong("order_id");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return orderId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDishOrderStatus(StatusType statusType, long orderDishId) {
        statusDao.insertStatusForDishOrder(orderDishId, statusType);
        long orderId = findOrderId(orderDishId);
        Order order = orderDao.getByID(orderId);

        if (order.allTheDishesFinished()){
            statusDao.insertStatusForOrder(orderId, StatusType.FINISHED);
        }
    }

    @Override
    public void addDishOrder(DishOrder dishOrder, long orderId) {
        try (Connection connection = datasource.getConnection()) {
            String sql =
                    "INSERT INTO order_dish (order_dish_id, dish_id, order_id, ordered_dish_quantity) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, dishOrder.getDishOrderId());
                statement.setLong(2, dishOrder.getCommendedDish().getDishId());
                statement.setLong(3, orderId);
                statement.setInt(4, dishOrder.getDishQuantityCommanded());
                statement.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error inserting into order_dish: " + e.getMessage());
                throw new RuntimeException(e);
            }
            statusDao.insertStatusForDishOrder(dishOrder.getDishOrderId(), StatusType.CREATED);
        } catch (SQLException e) {
            System.err.println("Error opening connection or preparing statement: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DishOrder> getDishOrdersByOrderId(long orderId) {
        List<DishOrder> dishOrders = new ArrayList<>();
        String sql = "SELECT order_dish_id, dish_id, order_id, ordered_dish_quantity FROM order_dish WHERE order_id = ?";

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long orderDishId = resultSet.getLong("order_dish_id");
                    long dishId = resultSet.getLong("dish_id");
                    int quantity = resultSet.getInt("ordered_dish_quantity");
                    DishOrder dishOrder = new DishOrder(orderDishId, dishesDao.getDishesById(dishId), quantity);
                    dishOrders.add(dishOrder);
                    dishOrder.setStatusList(statusDao.getStatusForDishOrder(orderDishId));
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return dishOrders;
    }

    @Override
    public void deleteDishOrder(long dishOrderId) {
        String sql = "DELETE FROM command_dish WHERE commanded_dish_id = ?";
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dishOrderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
