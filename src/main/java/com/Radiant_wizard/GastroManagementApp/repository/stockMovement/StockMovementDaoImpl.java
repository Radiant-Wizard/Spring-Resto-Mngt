package com.Radiant_wizard.GastroManagementApp.repository.stockMovement;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.stockMovement.StockMovementDto;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.MovementType;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementDaoImpl implements StockMovementDao {
    private final Datasource datasource;

    public StockMovementDaoImpl(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<StockMovement> getStockByIngredientId(long ingredientId) {
        List<StockMovement> stockList = new ArrayList<>();
        String sql = "SELECT ingredient_id, movement_date, movement_type, quantity, unit from stock_movement where ingredient_id = ?";

        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, ingredientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    stockList.add(new StockMovement(
                            ingredientId,
                            resultSet.getDouble("quantity"),
                            Unit.valueOf(resultSet.getString("unit")),
                            MovementType.valueOf(resultSet.getString("movement_type")),
                            resultSet.getObject("movement_date", LocalDateTime.class)
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockList;
    }

    @Override
    public void save(List<StockMovementDto> stockMovements, long ingredientId) {
        String sql =
                "INSERT INTO stock_movement (ingredient_id, movement_type, quantity, unit, movement_date) VALUES(?, ?::stock_movement_type, ?, ?::measurement_unit, ?::timestamp)";
        try (Connection connection = datasource.getConnection();){
            for (StockMovementDto stockMovement : stockMovements) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setLong(1, ingredientId);
                    preparedStatement.setString(2, stockMovement.getMovementType().toString());
                    preparedStatement.setDouble(3, stockMovement.getMovementQuantity());
                    preparedStatement.setString(4, stockMovement.getUnit().toString());
                    preparedStatement.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate()));
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
