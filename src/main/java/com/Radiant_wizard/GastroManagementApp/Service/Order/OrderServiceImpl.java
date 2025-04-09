package com.Radiant_wizard.GastroManagementApp.Service.Order;

import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import com.Radiant_wizard.GastroManagementApp.repository.DishOrderDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.DishesDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.OrderDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDaoImpl orderDao;
    private final DishOrderDaoImpl dishOrderDao;
    @Autowired
    private final DishesDaoImpl dishesDao;

    public OrderServiceImpl(OrderDaoImpl orderDao, DishOrderDaoImpl dishOrderDao, DishesDaoImpl dishesDao) {
        this.orderDao = orderDao;
        this.dishOrderDao = dishOrderDao;
        this.dishesDao = dishesDao;
    }

    @Override
    public List<Order> getAll(Integer pageSize, Integer pageNumber) {
        return orderDao.getAll(pageSize, pageNumber);
    }

    @Override
    public Order getOrderByRef(String reference) {
        return orderDao.getByReference(reference)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with reference: " + reference));
    }

    @Override
    public void addDishToOrder(Dish dish, Double quantity) {

    }
}
