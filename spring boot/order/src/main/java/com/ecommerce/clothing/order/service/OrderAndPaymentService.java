package com.ecommerce.clothing.order.service;

import com.ecommerce.clothing.order.model.response.CommonResponse;
import com.ecommerce.clothing.order.model.response.OrderAndPaymentResponse;
import com.ecommerce.clothing.order.repository.OrderAndPaymentRepository;
import com.ecommerce.clothing.order.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAndPaymentService {

    private final OrderAndPaymentRepository repository;

    private final HelperUtils utils;

    private final MongoTemplate mongoTemplate;

    public OrderAndPaymentResponse initiateOrder(String uniqueInteractionId) {
        return null;
    }

    public OrderAndPaymentResponse shipped(String uniqueInteractionId) {
        return null;
    }


    public OrderAndPaymentResponse updateOrder(String uniqueInteractionId) {
        return null;
    }

    public CommonResponse deleteOrder(String uniqueInteractionId) {
        return null;
    }

    public OrderAndPaymentResponse getOrder(String uniqueInteractionId) {
        return null;
    }

    public OrderAndPaymentResponse paymentComplete(String uniqueInteractionId) {
        return null;
    }
}
