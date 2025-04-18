package com.Radiant_wizard.GastroManagementApp.entity.model;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class DishOrder {
    private final long dishOrderId;
    private final Dish commendedDish;
    private final Integer dishQuantityCommanded;
    private List<Status> statusList;

    public DishOrder(long dishOrderId, Dish commendedDish, Integer dishQuantityCommanded) {
        this.dishOrderId = dishOrderId;
        this.commendedDish = commendedDish;
        this.dishQuantityCommanded = dishQuantityCommanded;
    }

    public StatusType getActualStatus(){
        return statusList
                .stream()
                .max(Comparator.comparing(Status::getCreationDate))
                .map(Status::getStatusType)
                .orElse(null);
    }

    enum TimeUnit { SECONDS, MINUTES, HOURS }
    enum Mode { AVG, MIN, MAX }

    public double calculateProcessingTime(TimeUnit unit, Mode mode) {
        Map<Long, List<Status>> grouped = this.statusList.stream()
                .collect(Collectors.groupingBy(status -> this.dishOrderId));

        List<Long> durationsInSeconds = new ArrayList<>();

        for (List<Status> group : grouped.values()) {
            LocalDateTime inProgress = null;
            LocalDateTime finished = null;

            for (Status e : group) {
                if (e.getStatusType() == StatusType.IN_PROGRESS){
                    inProgress = LocalDateTime.from(e.getCreationDate());
                } else if (e.getStatusType() == StatusType.FINISHED) {
                    finished = LocalDateTime.from(e.getCreationDate());
                }
            }

            if (inProgress != null && finished != null && finished.isAfter(inProgress)) {
                long seconds = Duration.between(inProgress, finished).getSeconds();
                durationsInSeconds.add(seconds);
            }
        }

        if (durationsInSeconds.isEmpty()) return 0;

        double result;
        switch (mode) {
            case MIN -> result = Collections.min(durationsInSeconds);
            case MAX -> result = Collections.max(durationsInSeconds);
            default -> result = durationsInSeconds.stream().mapToLong(Long::longValue).average().orElse(0);
        }

        return switch (unit) {
            case MINUTES -> result / 60;
            case HOURS -> result / 3600;
            default -> result;
        };
    }
}
