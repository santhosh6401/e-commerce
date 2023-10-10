package com.ecommerce.clothing.customer.model.request.profile;

import lombok.Data;

@Data
public class ProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String password;
}
