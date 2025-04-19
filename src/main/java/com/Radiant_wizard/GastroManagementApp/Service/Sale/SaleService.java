package com.Radiant_wizard.GastroManagementApp.Service.Sale;

import com.Radiant_wizard.GastroManagementApp.entity.model.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {
    List<Sale> getSale(LocalDateTime startTime, LocalDateTime endTime);
}
