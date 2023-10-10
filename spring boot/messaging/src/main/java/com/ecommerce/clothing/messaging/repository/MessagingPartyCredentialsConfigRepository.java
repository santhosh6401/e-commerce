package com.ecommerce.clothing.messaging.repository;

import com.ecommerce.clothing.messaging.model.entity.MessagingPartyCredentialsConfig;
import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessagingPartyCredentialsConfigRepository extends MongoRepository<MessagingPartyCredentialsConfig, String> {

    Optional<MessagingPartyCredentialsConfig> findByName(String credentialName);

    Optional<MessagingPartyCredentialsConfig> findByNameAndMessagingChannel(String credentialName, MessagingChannel mail);

}
