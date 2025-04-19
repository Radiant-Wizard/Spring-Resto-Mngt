package com.Radiant_wizard.AttendanceRecordApp;

import com.Radiant_wizard.GastroManagementApp.GastroManagementApp;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import com.Radiant_wizard.GastroManagementApp.repository.dishOrder.DishOrderDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.dish.DishesDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.order.OrderDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@SpringBootTest(classes = GastroManagementApp.class)
public class OrderTest {
    Logger logger = Logger.getLogger(OrderTest.class.getName());

    @Autowired
    private OrderDaoImpl orderDao;

    @Autowired
    private DishesDaoImpl dishesDao;

    @Autowired
    private DishOrderDaoImpl dishOrderDao;
    @Test
    public void testGetAll(){
        List<Order> orders = orderDao.getAll(1, 1);
        Assertions.assertEquals(1, orders.size());
    }

    @Test
    public void testGetRef(){
        Optional<Order> order = orderDao.getByReference("CMD001");

        Assertions.assertTrue(order.isPresent(), "Order with reference CMD001 should exist");
        Assertions.assertEquals("CMD001", order.get().getReference());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testCreateOrder() throws SQLException {
        // Arrange
        Order order = new Order(4, "CMD004");

        // Act
        orderDao.createOrder(order);
        Optional<Order> retrieveSavedOrder = orderDao.getByReference("CMD004");

        // Assert
        Assertions.assertNotNull(retrieveSavedOrder);
        Assertions.assertTrue(retrieveSavedOrder.isPresent());
        Assertions.assertEquals("CMD004", retrieveSavedOrder.get().getReference());
        Assertions.assertEquals(StatusType.CREATED.toString(), retrieveSavedOrder.get().getActualStatus().toString());
        System.out.println("✅ Creating new Order PASSED");
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testConfirmOrder() throws SQLException, IllegalAccessException {
        List<DishOrder> orderedDishes = List.of(
                new DishOrder(6, dishesDao.getDishesById(1), 2)
        );

        Order order = new Order(5L, "CMD005");
        orderDao.createOrder(order);

        Order retrieveOrder5 = orderDao.getByID(5L);
        retrieveOrder5.addDishToOrder(orderedDishes);

        orderDao.save(retrieveOrder5);

        Order orderAfterSave = orderDao.getByReference("CMD005").get();
        List<DishOrder> dishOrders = dishOrderDao.getDishOrdersByOrderId(5L);

        Assertions.assertEquals(StatusType.CONFIRMED.toString(), orderAfterSave.getActualStatus().toString());
        dishOrders.forEach(dishOrder -> {
            Assertions.assertEquals(StatusType.CONFIRMED.toString(), dishOrder.getActualStatus().toString());
        });
        Assertions.assertFalse(dishOrders.isEmpty());
        System.out.println("✅ Creating new Order PASSED");
        System.out.println();
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void testGetStatus() throws SQLException, IllegalAccessException {
        // Arrange
        Optional<Order> order = orderDao.getByReference("CMD004");

        // Act
        StatusType orderStatus;
        if (order.isPresent()) {
            orderStatus = order.get().getActualStatus();
        } else {
            throw new RuntimeException("order CMD004 has not been found");
        }
        // Assert
        Assertions.assertEquals(StatusType.IN_PROGRESS, orderStatus);

        System.out.println("✅ Getting actual Status PASSED");
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void testValidateOrder() throws SQLException, IllegalAccessException {
        Order impOrder = new Order(6, "CMD006");
        orderDao.createOrder(impOrder);

        List<DishOrder> orderedDishes = List.of(
                new DishOrder(8, dishesDao.getDishesById(1), 2000)
        );
        Order retrieveOrder6 = orderDao.getByID(6L);
        retrieveOrder6.addDishToOrder(orderedDishes);

        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    orderDao.save(retrieveOrder6);
                });
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void testUpdateStatus() {
        // Arrange
        Order order = orderDao.getByID(4L);

        // Act
        orderDao.updateStatus(StatusType.IN_PROGRESS, order.getOrderID());

        Order orderAfterUpdate = orderDao.getByID(4L);

        Assertions.assertEquals(StatusType.IN_PROGRESS, orderAfterUpdate.getActualStatus());
    }

    @Test
    void testUpdateFinished(){
        Order order = orderDao.getByID(5L);

        // Act

        Assertions.assertThrows(RuntimeException.class, () -> {
                orderDao.updateStatus(StatusType.FINISHED, order.getOrderID());}
        );
    }

    @Test
    void testTotalAmount(){
        Optional<Order> order = orderDao.getByReference("CMD005");


        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(16000.0, order.get().getTotalAmount());

    }

    @Test
    void testSaveImpossible() throws SQLException, IllegalAccessException {
        List<DishOrder> dishOrders = List.of(
                new DishOrder(1, dishesDao.getDishesById(1), 2000)
        );
        Optional<Order> order = orderDao.getByReference("ORDER10");
        Assertions.assertTrue(order.isPresent());
        Order saveOrder = order.get();
        order.get().addDishToOrder(dishOrders);

        Assertions.assertThrows(RuntimeException.class, () -> {
            orderDao.save(saveOrder);
        });
    }
}
