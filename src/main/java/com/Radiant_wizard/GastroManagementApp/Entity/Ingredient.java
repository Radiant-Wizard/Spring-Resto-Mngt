package com.Radiant_wizard.GastroManagementApp.Entity;

import com.Radiant_wizard.GastroManagementApp.Entity.Enum.MovementType;
import com.Radiant_wizard.GastroManagementApp.Entity.Enum.Unit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class Ingredient {
    private final Long ingredientId;
    private final String ingredientName;
    private final LocalDateTime lastModification;
    private final Unit unit;
    private double neededQuantity;
    private final List<Price> unitPrice;
    private final List<StockMovement> stockMovements;


    public Ingredient(Long ingredientId, String ingredientName, LocalDateTime lastModification, Unit unit, List<Price> unitPrice, List<StockMovement> stockMovements) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.lastModification = lastModification;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.stockMovements = stockMovements;
    }

    public Ingredient(Long ingredientId, String ingredientName, LocalDateTime lastModification, Unit unit, List<Price> unitPrice, List<StockMovement> stockMovements, double neededQuantity) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.lastModification = lastModification;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.neededQuantity = neededQuantity;
        this.stockMovements = stockMovements;
    }

    public Price getNearestPrice(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return this.unitPrice.stream().max(Comparator.comparing(Price::getModificationDate)).get();
        }

        long smallestDifference = Long.MAX_VALUE;
        Price nearestPriceToTheGivenDate = null;
        for (Price price : this.unitPrice) {
            long difference = ChronoUnit.MILLIS.between(localDateTime, price.getModificationDate());
            if (difference < smallestDifference) {
                smallestDifference = difference;
                nearestPriceToTheGivenDate = price;
            }
        }
        return nearestPriceToTheGivenDate;
    }


    public double getAvailableQuantity(LocalDateTime ofThisDate) {
        LocalDateTime localDateTime = (ofThisDate == null ? LocalDateTime.now(): ofThisDate);
//        double currentAvailableQuantity = 0.0;
//        for (StockMovement stockMovement : this.stockMovements) {
//            if (stockMovement.getMovementDate().isBefore(localDateTime) || stockMovement.getMovementDate().isEqual(localDateTime)) {
//                if (stockMovement.getMovementType().equals(MovementType.ENTRY)) {
//                    currentAvailableQuantity += stockMovement.getMovementQuantity();
//                } else if (stockMovement.getMovementType().equals(MovementType.EXIT)) {
//                    currentAvailableQuantity -= stockMovement.getMovementQuantity();
//                }
//            }
//        }
        Double totalEntryQuantity = this.stockMovements
                .stream()
                .filter(stockMovement -> (stockMovement.getMovementDate().isBefore(localDateTime) || stockMovement.getMovementDate().isEqual(localDateTime)) && stockMovement.getMovementType() == MovementType.ENTRY)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);
        Double totalExitQuantity = this.stockMovements
                .stream()
                .filter(stockMovement -> (stockMovement.getMovementDate().isBefore(localDateTime) || stockMovement.getMovementDate().isEqual(localDateTime)) && stockMovement.getMovementType() == MovementType.EXIT)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);
        return totalEntryQuantity - totalExitQuantity;
    }

    public double getAvailableQuantity(){
        Double totalEntryQuantity = this.stockMovements
                .stream()
                .filter(stockMovement -> stockMovement.getMovementType() == MovementType.ENTRY)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);
        Double totalExitQuantity = this.stockMovements
                .stream()
                .filter(stockMovement ->  stockMovement.getMovementType() == MovementType.EXIT)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);
        return totalEntryQuantity - totalExitQuantity;
    }

}
