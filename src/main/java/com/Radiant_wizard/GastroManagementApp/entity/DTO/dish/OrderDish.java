package com.Radiant_wizard.GastroManagementApp.entity.DTO.dish;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDish {
    private long dishId;
    private String name;
    private Integer quantity;
    private StatusType status;

    public OrderDish(long dishId, Integer quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public OrderDish(long dishId, String name, Integer quantity, StatusType status) {
        this.dishId = dishId;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }
}
