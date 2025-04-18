package com.Radiant_wizard.GastroManagementApp.mapper;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.stockMovement.StockMovementDto;
import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {
    public StockMovementDto mapToDto(StockMovement stockMovement){
        StockMovementDto stockMovementDto = new StockMovementDto();
        stockMovementDto.setMovementType(stockMovement.getMovementType());
        stockMovementDto.setMovementDate(stockMovement.getMovementDate());
        stockMovementDto.setUnit(stockMovement.getUnit());
        stockMovementDto.setMovementQuantity(stockMovement.getMovementQuantity());
        return stockMovementDto;
    }

}
