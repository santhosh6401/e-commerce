package com.ecommerce.clothing.messaging.model.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatusLifeCycle {
    private String statusDescription;
    private LocalDateTime createdOn;
}
