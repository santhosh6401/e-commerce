package com.ecommerce.clothing.customer.repository;

import com.ecommerce.clothing.customer.model.entity.ComplaintsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintsRepository extends MongoRepository<ComplaintsEntity, String> {
}
