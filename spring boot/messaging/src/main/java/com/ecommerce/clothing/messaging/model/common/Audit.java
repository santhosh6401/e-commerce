package com.ecommerce.clothing.messaging.model.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Audit {
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
