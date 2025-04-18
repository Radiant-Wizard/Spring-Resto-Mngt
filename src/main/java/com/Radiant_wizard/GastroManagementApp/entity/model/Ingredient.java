package com.Radiant_wizard.GastroManagementApp.entity.model;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.MovementType;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
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
    private final Unit unit;
    private double neededQuantity;
    private final List<Price> unitPrice;
    private final List<StockMovement> stockMovements;


    public Ingredient(Long ingredientId, String ingredientName,Unit unit, List<Price> unitPrice, List<StockMovement> stockMovements) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.stockMovements = stockMovements;
    }

    public Ingredient(Long ingredientId, String ingredientName, Unit unit, List<Price> unitPrice, List<StockMovement> stockMovements, double neededQuantity) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
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

    public Price getNearestPrice() {
        return this.unitPrice.stream().max(Comparator.comparing(Price::getModificationDate)).get();
    }

    public LocalDateTime getLastModificationDate() {
        return this.unitPrice.stream().max(Comparator.comparing(Price::getModificationDate)).get().getModificationDate();
    }


    public double getAvailableQuantity(LocalDateTime ofThisDate) {
        LocalDateTime localDateTime = (ofThisDate == null ? LocalDateTime.now(): ofThisDate);
        Double totalEntryQuantity = this.stockMovements
                .stream()
                .filter(stockMovement -> (stockMovement.getMovementDate().isBefore(localDateTime) || stockMovement.getMovementDate().isEqual(localDateTime)) && stockMovement.getMovementType() == MovementType.IN)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);
        Double totalExitQuantity = this.stockMovements
                .stream()
                .filter(stockMovement -> (stockMovement.getMovementDate().isBefore(localDateTime) || stockMovement.getMovementDate().isEqual(localDateTime)) && stockMovement.getMovementType() == MovementType.OUT)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);
        return totalEntryQuantity - totalExitQuantity;
    }

    public double getAvailableQuantity(){
        Double totalEntryQuantity = this.stockMovements
                .stream()
                .filter(stockMovement -> stockMovement.getMovementType() == MovementType.IN)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);

        Double totalExitQuantity = this.stockMovements
                .stream()
                .filter(stockMovement ->  stockMovement.getMovementType() == MovementType.OUT)
                .map(StockMovement::getMovementQuantity)
                .reduce(0.0, Double::sum);

        return totalEntryQuantity - totalExitQuantity;
    }

}
