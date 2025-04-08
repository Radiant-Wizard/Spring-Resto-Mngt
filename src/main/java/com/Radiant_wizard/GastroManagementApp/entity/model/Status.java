package com.Radiant_wizard.GastroManagementApp.entity.model;

import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import lombok.Data;

import java.time.Instant;

@Data
public class Status {
    private final StatusType statusType;
    private final Instant creationDate;

    public Status(StatusType statusType, Instant creationDate){
        this.statusType = statusType;
        this.creationDate = creationDate;
    }
}
