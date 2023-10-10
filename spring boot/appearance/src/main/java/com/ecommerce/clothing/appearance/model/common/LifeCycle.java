package com.ecommerce.clothing.appearance.model.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LifeCycle {
    private String statusDescription;
    private LocalDateTime createdOn;
}
