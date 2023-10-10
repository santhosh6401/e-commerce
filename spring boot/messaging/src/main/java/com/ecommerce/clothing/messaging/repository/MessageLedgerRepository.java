package com.ecommerce.clothing.messaging.repository;

import com.ecommerce.clothing.messaging.model.entity.MessageLedger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageLedgerRepository extends MongoRepository<MessageLedger, String> {
}
