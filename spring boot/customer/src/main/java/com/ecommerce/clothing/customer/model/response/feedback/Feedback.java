package com.ecommerce.clothing.customer.model.response.feedback;

import lombok.Data;

@Data
public class Feedback {
    private String name;
    private String email;
    private String phoneNo;
    private String message;
}
