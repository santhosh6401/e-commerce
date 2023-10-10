package com.ecommerce.clothing.appearance.repository;

import com.ecommerce.clothing.appearance.model.entity.AppearanceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppearanceRepository extends MongoRepository<AppearanceEntity, String> {
    Optional<AppearanceEntity> findByAppearanceName(String name);
}
