package com.ecommerce.clothing.customer.repository;

import com.ecommerce.clothing.customer.model.entity.FAQsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQsRepository extends MongoRepository<FAQsEntity, String> {
}
