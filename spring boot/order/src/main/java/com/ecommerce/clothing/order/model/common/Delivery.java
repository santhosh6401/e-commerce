package com.ecommerce.clothing.order.model.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Delivery {
    private String deliveryTrackId;
    private LocalDateTime shippingDate;
    private LocalDateTime deliveryDate;
}
