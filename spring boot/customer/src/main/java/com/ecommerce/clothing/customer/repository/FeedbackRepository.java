package com.ecommerce.clothing.customer.repository;

import com.ecommerce.clothing.customer.model.entity.FeedbackEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends MongoRepository<FeedbackEntity, String> {
}
