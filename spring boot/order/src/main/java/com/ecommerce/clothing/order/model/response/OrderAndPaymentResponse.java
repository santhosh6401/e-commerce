package com.ecommerce.clothing.order.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAndPaymentResponse {
    private List<OrderAndPayment> orderAndPayments = new ArrayList<>();
    private String response;
}
