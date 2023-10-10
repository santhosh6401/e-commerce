package com.ecommerce.clothing.messaging.model.request.messagecontent;

import lombok.Data;

import java.util.Map;

@Data
public class DynamicValueRequest {
    private Map<String, String> dynamicValue;
}
