package com.Radiant_wizard.GastroManagementApp.entity.DTO.order;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.OrderDish;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;


@Data
@AllArgsConstructor
public class OrderDto {
    private final String reference;
    private final StatusType Status;
    private final List<OrderDish> orderDish;
}
