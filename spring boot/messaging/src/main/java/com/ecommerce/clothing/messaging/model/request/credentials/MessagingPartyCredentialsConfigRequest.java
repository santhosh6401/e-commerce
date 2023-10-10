package com.ecommerce.clothing.messaging.model.request.credentials;

import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import lombok.Data;

import java.util.Map;

@Data
public class MessagingPartyCredentialsConfigRequest {
    private String name;
    private Map<String, String> credentials;
    private MessagingChannel messagingChannel;
}
