package com.ecommerce.clothing.customer.model.response.complaint;

import lombok.Data;

@Data
public class Complaint {
    private String name;
    private String email;
    private String phoneNo;
    private String message;
}
