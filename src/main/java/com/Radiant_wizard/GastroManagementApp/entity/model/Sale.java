package com.Radiant_wizard.GastroManagementApp.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class Sale {
    private String dishName;
    private Double saleQuantity;
    private Double totalEarned;
}
