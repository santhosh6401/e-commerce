package com.ecommerce.clothing.messaging.model.response.credentials;

import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessagingPartyCredentialConfigResponse {
    private String name;
    private Map<String, String> credentials;
    private MessagingChannel messagingChannel;
    private String response;
}
