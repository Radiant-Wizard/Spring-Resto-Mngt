package com.Radiant_wizard.GastroManagementApp.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IngredientResponseEntity {
    private long id;
    private String name;
    private Double unitPrice;
    private LocalDateTime updatedAt;
}

