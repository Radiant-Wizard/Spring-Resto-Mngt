package com.Radiant_wizard.GastroManagementApp.Service.Dish;

import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Mode;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import com.Radiant_wizard.GastroManagementApp.entity.model.Status;
import com.Radiant_wizard.GastroManagementApp.mapper.DishMapper;
import com.Radiant_wizard.GastroManagementApp.repository.dish.DishesDaoImpl;
import com.Radiant_wizard.GastroManagementApp.repository.dishOrder.DishOrderDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishesDaoImpl dishesDao;
    private final DishOrderDaoImpl dishOrderDao;
    @Autowired
    private final DishMapper dishMapper;

    @Autowired
    public DishServiceImpl(DishesDaoImpl dishesDao, DishOrderDaoImpl dishOrderDao, DishMapper dishMapper) {
        this.dishesDao = dishesDao;
        this.dishOrderDao = dishOrderDao;
        this.dishMapper = dishMapper;
    }

    @Override
    public void saveDishes(List<com.Radiant_wizard.GastroManagementApp.entity.model.Dish> dish) {
        try {
            dishesDao.saveDishes(dish);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> getAllDishes(int pageSize, int pageNumber) {
        return dishesDao.getAll(pageSize, pageNumber).stream().map(dishMapper::dishDbToDish).toList();
    }

    @Override
    public double calculateProcessingTimeForDishOrders(long dishId, LocalDateTime start, LocalDateTime end, TimeUnit unit, Mode mode) {
        Map<Long, DishOrder> dishOrderMap = dishOrderDao.dishOrderByDishId(dishId, start, end);

        List<Long> durationsInSeconds = new ArrayList<>();

        for (DishOrder dishOrder : dishOrderMap.values()) {
            List<Status> statuses = dishOrder.getStatusList().stream()
                    .sorted(Comparator.comparing(Status::getCreationDate))
                    .toList();

            Status inProgressStatus = null;

            for (Status status : statuses) {
                if (status.getStatusType() == StatusType.IN_PROGRESS) {
                    inProgressStatus = status;
                } else if (status.getStatusType() == StatusType.FINISHED && inProgressStatus != null) {
                    Instant inProgressTime = inProgressStatus.getCreationDate();
                    Instant finishedTime = status.getCreationDate();

                    if (finishedTime.isAfter(inProgressTime)) {
                        long seconds = Duration.between(inProgressTime, finishedTime).getSeconds();
                        durationsInSeconds.add(seconds);
                    }

                    inProgressStatus = null;
                }
            }
        }

        if (durationsInSeconds.isEmpty()) return 0;

        double result;
        switch (mode) {
            case MIN -> result = Collections.min(durationsInSeconds);
            case MAX -> result = Collections.max(durationsInSeconds);
            case AVG -> result = durationsInSeconds.stream().mapToDouble(Long::doubleValue).average().orElse(0);
            default -> result = durationsInSeconds.stream().mapToLong(Long::longValue).average().orElse(0);
        }

        return switch (unit) {
            case MINUTES -> result / 60;
            case HOURS -> result / 3600;
            default -> result;
        };
    }

    @Override
    public Map<Long, DishOrder> dishOrderByDishId(long dishId, LocalDateTime start, LocalDateTime end){
        try {
            return dishOrderDao.dishOrderByDishId(dishId, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
