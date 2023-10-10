package com.ecommerce.clothing.customer.repository;

import com.ecommerce.clothing.customer.model.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface CartRepository extends MongoRepository<CartEntity, String> {
    Optional<CartEntity> findByEmailAndPhoneNoAndName(String email, String phoneNo, String name);
}
