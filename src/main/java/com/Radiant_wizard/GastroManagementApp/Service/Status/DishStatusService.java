package com.Radiant_wizard.GastroManagementApp.Service.Status;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.order.OrderDto;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.Status;

public interface DishStatusService {
    OrderDto updateDishStatus(StatusType status, long dishId, String orderRef);
}
