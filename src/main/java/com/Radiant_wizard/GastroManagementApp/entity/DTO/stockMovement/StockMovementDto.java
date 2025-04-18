package com.Radiant_wizard.GastroManagementApp.entity.DTO.stockMovement;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.MovementType;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockMovementDto {
    private double movementQuantity;
    private MovementType movementType;
    private LocalDateTime movementDate;
    private Unit unit;
}
