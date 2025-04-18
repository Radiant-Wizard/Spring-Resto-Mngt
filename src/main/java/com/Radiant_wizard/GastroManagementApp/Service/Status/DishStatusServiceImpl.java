package com.Radiant_wizard.GastroManagementApp.Service.Status;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.order.OrderDto;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import com.Radiant_wizard.GastroManagementApp.mapper.OrderMapper;
import com.Radiant_wizard.GastroManagementApp.repository.dishOrder.DishOrderDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.order.OrderDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.status.StatusDaoImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishStatusServiceImpl implements DishStatusService{
    private final DishOrderDaoImpl dishOrderDao;
    private final StatusDaoImpl statusDao;
    private final OrderDaoImpl orderDao;
    private final OrderMapper orderMapper;
    public DishStatusServiceImpl(DishOrderDaoImpl dishOrderDao, StatusDaoImpl statusDao, OrderDaoImpl orderDao, OrderMapper orderMapper) {
        this.dishOrderDao = dishOrderDao;
        this.statusDao = statusDao;
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto updateDishStatus(StatusType status, long dishId, String orderRef) {
        try {
            long orderDishId = dishOrderDao.findDishOrderIdByDish(dishId);
            dishOrderDao.updateDishOrderStatus(status, orderDishId);
            Optional<Order> order = orderDao.getByReference(orderRef);
            if (order.isEmpty()){
                throw new RuntimeException("This order has not been found");
            } else {
                return orderMapper.mapToDto(order.get());
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
