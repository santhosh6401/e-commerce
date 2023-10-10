package com.ecommerce.clothing.customer.model.request.profile;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
