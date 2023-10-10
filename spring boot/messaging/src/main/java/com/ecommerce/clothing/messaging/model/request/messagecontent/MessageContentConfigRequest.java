package com.ecommerce.clothing.messaging.model.request.messagecontent;

import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import lombok.Data;

import java.util.Map;

@Data
public class MessageContentConfigRequest {
    private String name;
    private MessagingChannel messagingChannel;
    private Map<String, String> messageContent;
}
