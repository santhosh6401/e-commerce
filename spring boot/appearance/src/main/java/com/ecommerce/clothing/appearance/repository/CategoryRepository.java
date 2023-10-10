package com.ecommerce.clothing.appearance.repository;

import com.ecommerce.clothing.appearance.model.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {
    Optional<CategoryEntity> findByCategoryName(String name);
}
