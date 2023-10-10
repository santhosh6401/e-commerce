package com.ecommerce.clothing.messaging.model.entity;


import com.ecommerce.clothing.messaging.model.common.Audit;
import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "messaging_party_credentials")
public class MessagingPartyCredentialsConfig {

    @Id
    private String id;
    private String name;
    private MessagingChannel messagingChannel;
    private Map<String, String> credentials;
    private Audit audit;

}
