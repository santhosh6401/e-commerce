package com.ecommerce.clothing.customer.model.request.faqs;

import lombok.Data;

@Data
public class FAQsRequest {
    private String id;
    private String query;
    private String answer;
}
