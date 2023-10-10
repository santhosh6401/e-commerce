package com.ecommerce.clothing.messaging.model.response.messagecontent;

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
public class MessageContentConfigResponse {
    private String name;
    private MessagingChannel notificationType;
    private Map<String, String> messageContent;
    private String response;
}
