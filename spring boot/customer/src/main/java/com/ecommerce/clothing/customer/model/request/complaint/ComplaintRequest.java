package com.ecommerce.clothing.customer.model.request.complaint;

import lombok.Data;

@Data
public class ComplaintRequest {
    private String name;
    private String email;
    private String phoneNo;
    private String message;
}
