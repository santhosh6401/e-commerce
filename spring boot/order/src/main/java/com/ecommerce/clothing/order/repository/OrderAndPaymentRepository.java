package com.ecommerce.clothing.order.repository;

import com.ecommerce.clothing.order.model.entity.OrderAndPaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderAndPaymentRepository extends MongoRepository<OrderAndPaymentEntity, String> {
}
