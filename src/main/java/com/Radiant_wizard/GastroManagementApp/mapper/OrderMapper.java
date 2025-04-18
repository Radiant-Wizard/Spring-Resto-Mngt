package com.Radiant_wizard.GastroManagementApp.mapper;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.order.OrderDto;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    DishMapper dishMapper;
    public OrderMapper(DishMapper dishMapper){
        this.dishMapper = dishMapper;
    }
    public OrderDto mapToDto(Order order){
        return new OrderDto(
                order.getReference(),
                order.getActualStatus(),
                order.getOrderedDish().stream().map(dishOrder -> dishMapper.mapToOrderDish(dishOrder)).toList()
        );
    }
}
