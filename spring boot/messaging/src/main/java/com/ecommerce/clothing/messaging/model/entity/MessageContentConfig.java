package com.ecommerce.clothing.messaging.model.entity;


import com.ecommerce.clothing.messaging.model.common.Audit;
import com.ecommerce.clothing.messaging.model.common.StatusLifeCycle;
import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Document("message-config")
public class MessageContentConfig {
    @Id
    private String id;
    private String name;
    private MessagingChannel messagingChannel;
    private Map<String, String> messageContent;
    private List<StatusLifeCycle> statusLifeCycles = new ArrayList<>();
    private Audit audit;
}
