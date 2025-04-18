package com.Radiant_wizard.GastroManagementApp.repository.status;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.Status;

import java.util.List;

public interface StatusDao {
    List<Status> getStatusForOrder(long orderId);
    List<Status> getStatusForDishOrder(long dishOrderId);
    void insertStatusForOrder(long orderId, StatusType status);
    void insertStatusForDishOrder(long dishOrderId, StatusType statusType);
}
