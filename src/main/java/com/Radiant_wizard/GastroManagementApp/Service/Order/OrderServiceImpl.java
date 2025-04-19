package com.Radiant_wizard.GastroManagementApp.Service.Order;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.OrderDish;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.order.OrderDto;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import com.Radiant_wizard.GastroManagementApp.mapper.OrderMapper;
import com.Radiant_wizard.GastroManagementApp.repository.dishOrder.DishOrderDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.dish.DishesDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.order.OrderDaoImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDaoImpl orderDao;
    private final DishOrderDaoImpl dishOrderDao;
    private final DishesDaoImpl dishesDao;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderDaoImpl orderDao, DishOrderDaoImpl dishOrderDao, DishesDaoImpl dishesDao, OrderMapper orderMapper) {
        this.orderDao = orderDao;
        this.dishOrderDao = dishOrderDao;
        this.dishesDao = dishesDao;
        this.orderMapper =orderMapper;
    }

    @Override
    public List<Order> getAll(Integer pageSize, Integer pageNumber) {
        return orderDao.getAll(pageSize, pageNumber);
    }

    @Override
    public OrderDto getOrderByRef(String reference) {
        Order order= orderDao.getByReference(reference)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with reference: " + reference));
        return orderMapper.mapToDto(order);

    }

    @Override
    public void createOrder(Order order) {
        try {
            orderDao.createOrder(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderDto addDishesToOrder(List<OrderDish> orderDishes, String reference) throws SQLException {
        Optional<Order> order = orderDao.getByReference(reference);
        if (order.isPresent()){
            Order order1 = order.get();
            List<DishOrder> dishOrders = orderDishes.stream().map(orderDish -> {
                try {
                    return new DishOrder(
                            orderDish.getDishId(),
                            dishesDao.getDishesById(orderDish.getDishId()),
                            orderDish.getQuantity()
                    );
                } catch (SQLException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            order1.addDishToOrder(dishOrders);
            orderDao.save(order1);
            Order retrieveSavedOrder = orderDao.getByID(order1.getOrderID());
            return orderMapper.mapToDto(retrieveSavedOrder);
        } else {
            throw new RuntimeException("The order " + reference + " has not been found");
        }
    }
}
