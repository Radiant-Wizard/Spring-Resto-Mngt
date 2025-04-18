package com.Radiant_wizard.GastroManagementApp.repository.status;

import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.Status;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatusDaoImpl implements StatusDao{
    Datasource datasource;

    public StatusDaoImpl(Datasource datasource){
        this.datasource = datasource;
    }

    @Override
    public List<Status> getStatusForOrder(long orderId) {
        List<Status> statusList = new ArrayList<>();
        String query =
                "SELECT order_id, order_creation_date, order_status from order_status where order_id = ?";
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    statusList.add(new Status(
                            StatusType.valueOf(resultSet.getString("order_status")),
                            resultSet.getTimestamp("order_creation_date").toInstant()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statusList;
    }

    @Override
    public List<Status> getStatusForDishOrder(long dishOrderId) {
        List<Status> statusList = new ArrayList<>();
        String query =
                "SELECT order_dish_id, order_dish_creation_date, order_dish_status from order_dish_status where order_dish_id = ?";
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, dishOrderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    statusList.add(new Status(
                            StatusType.valueOf(resultSet.getString("order_dish_status")),
                            resultSet.getTimestamp("order_dish_creation_date").toInstant()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statusList;
    }

    @Override
    public void insertStatusForOrder(long orderId, StatusType status) {
        String statusInsert =
                "insert into order_status (order_id, order_status, order_creation_date ) values (?, ?::statusType, ?::TIMESTAMP) ON CONFLICT DO NOTHING;";
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement1 = connection.prepareStatement(statusInsert)) {
            statement1.setLong(1, orderId);
            statement1.setString(2, status.toString());
            statement1.setTimestamp(3, Timestamp.from(Instant.now()));
            statement1.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertStatusForDishOrder(long orderDishId, StatusType statusType) {
        String sql = "INSERT INTO order_dish_status (order_dish_id, order_dish_status, order_dish_creation_date) values(?,?::statusType,?::TIMESTAMP);";

        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, orderDishId);
            preparedStatement.setString(2, statusType.toString());
            preparedStatement.setTimestamp(3, Timestamp.from(Instant.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
