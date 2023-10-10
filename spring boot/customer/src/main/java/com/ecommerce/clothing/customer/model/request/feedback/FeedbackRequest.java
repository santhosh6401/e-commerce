package com.ecommerce.clothing.customer.model.request.feedback;

import lombok.Data;

@Data
public class FeedbackRequest {
    private String name;
    private String email;
    private String phoneNo;
    private String message;
}
