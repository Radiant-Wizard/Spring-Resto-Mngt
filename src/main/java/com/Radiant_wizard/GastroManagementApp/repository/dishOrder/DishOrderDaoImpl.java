package com.Radiant_wizard.GastroManagementApp.repository.dishOrder;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import com.Radiant_wizard.GastroManagementApp.entity.model.Status;
import com.Radiant_wizard.GastroManagementApp.repository.dish.DishesDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.order.OrderDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.status.StatusDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DishOrderDaoImpl implements DishOrderDao {
    private final Datasource datasource;
    private final DishesDaoImpl dishesDao;
    private final StatusDaoImpl statusDao;
    private OrderDaoImpl orderDao;

    @Autowired
    public DishOrderDaoImpl(
            Datasource datasource,
            DishesDaoImpl dishesDao,
            StatusDaoImpl statusDao,
            @Lazy OrderDaoImpl orderDao
    ) {
        this.datasource = datasource;
        this.dishesDao = dishesDao;
        this.statusDao = statusDao;
        this.orderDao = orderDao;
    }

    public DishOrderDaoImpl(
            Datasource datasource,
            DishesDaoImpl dishesDao,
            StatusDaoImpl statusDao
    ) {
        this.datasource = datasource;
        this.dishesDao = dishesDao;
        this.statusDao = statusDao;
    }


    private long findOrderId(long orderDishId) {
        String sql = "SELECT order_dish_id, dish_id, order_id, ordered_dish_quantity from order_dish where order_dish_id = ?";
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
    public long findDishOrderIdByDish(long dishId) {
        String sql = "SELECT order_dish_id, dish_id, order_id, ordered_dish_quantity from order_dish where dish_id = ?";
        long orderId = 0;

        try (Connection connection = datasource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, dishId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderId = resultSet.getLong("order_dish_id");
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

        if (statusType == StatusType.CREATED || statusType == StatusType.CONFIRMED){
            throw new RuntimeException("You can't do that");
        }
        if (statusType == StatusType.IN_PROGRESS){
        }

        if (order.allTheDishesFinished()) {
            statusDao.insertStatusForOrder(orderId, StatusType.FINISHED);
            statusDao.insertStatusForOrder(orderId, StatusType.SERVED);
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
            statusDao.insertStatusForDishOrder(dishOrder.getDishOrderId(), StatusType.CONFIRMED);
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

    @Override
    public Map<Long, DishOrder> dishOrderByDishId(long dishId, LocalDateTime start, LocalDateTime end) {
        Map<Long, DishOrder> dishOrderMap = new HashMap<>();
        String sql = """
                  SELECT
                        od.order_dish_id,
                        od.dish_id,
                        d.dish_name,
                        d.dish_price,
                        ods.order_dish_status,
                        ods.order_dish_creation_date,
                        od.ordered_dish_quantity
                    FROM
                        order_dish_status ods
                    JOIN
                        order_dish od ON od.order_dish_id = ods.order_dish_id
                    JOIN
                        dishes d ON d.dish_id = od.dish_id
                    WHERE
                        od.dish_id = ?
                        AND ods.order_dish_creation_date BETWEEN ? AND ?
                        AND ods.order_dish_status IN ('IN_PROGRESS', 'FINISHED')
                    ORDER BY
                        od.order_dish_id, ods.order_dish_creation_date
                """;

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dishId);
            statement.setTimestamp(2, Timestamp.valueOf(start));
            statement.setTimestamp(3, Timestamp.valueOf(end));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long dishOrderId = resultSet.getLong("order_dish_id");
                    DishOrder dishOrder = dishOrderMap.get(dishOrderId);
                    if (dishOrder == null){
                        long dishIdDb = resultSet.getLong("dish_id");
                        Integer quantity = resultSet.getInt("ordered_dish_quantity");
                        dishOrder = new DishOrder(dishOrderId, dishesDao.getDishesById(dishIdDb), quantity);
                        dishOrder.setStatusList(new ArrayList<>());
                        dishOrderMap.put(dishOrderId, dishOrder);
                    }
                    StatusType type = StatusType.valueOf(resultSet.getString("order_dish_status"));
                    Instant date = resultSet.getTimestamp("order_dish_creation_date").toInstant();
                    dishOrder.getStatusList().add(new Status(type, date));
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return dishOrderMap;
    }

}
