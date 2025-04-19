package com.Radiant_wizard.GastroManagementApp.repository.sale;

import com.Radiant_wizard.GastroManagementApp.entity.model.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleDao {
    List<Sale> getSale(LocalDateTime from, LocalDateTime to);
}
