package com.Radiant_wizard.GastroManagementApp.entity.model;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.MovementType;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class StockMovement {
    private final long ingredientId;
    private long stockMovementId;
    private final double movementQuantity;
    private final MovementType movementType;
    private final LocalDateTime movementDate;
    private final Unit unit;

    public StockMovement(long ingredientId, double movementQuantity,  Unit unit,MovementType movementType, LocalDateTime movementDate) {
        this.ingredientId = ingredientId;
        this.movementQuantity = movementQuantity;
        this.movementType = movementType;
        this.movementDate = movementDate;
        this.unit = unit;
    }

    public StockMovement(long stockMovementId, long ingredientId, double movementQuantity, Unit unit, MovementType movementType, LocalDateTime movementDate) {
        this.stockMovementId = stockMovementId;
        this.ingredientId = ingredientId;
        this.movementQuantity = movementQuantity;
        this.unit = unit;
        this.movementType = movementType;
        this.movementDate = movementDate;
    }

}

