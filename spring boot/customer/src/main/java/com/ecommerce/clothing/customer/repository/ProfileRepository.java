package com.ecommerce.clothing.customer.repository;

import com.ecommerce.clothing.customer.model.entity.ProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String passwordEncode);

    Optional<ProfileEntity> findByEmailAndPasswordResetKey(String email, String key);
}
