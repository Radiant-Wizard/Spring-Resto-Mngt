package com.Radiant_wizard.AttendanceRecordApp;

import com.Radiant_wizard.GastroManagementApp.AttendanceRecordAppApplication;
import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import com.Radiant_wizard.GastroManagementApp.repository.OrderDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@SpringBootTest(classes = AttendanceRecordAppApplication.class)
public class OrderTest {
    Logger logger = Logger.getLogger(OrderTest.class.getName());

    @Autowired
    private OrderDaoImpl orderDao;


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
}
