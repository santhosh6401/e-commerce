package com.ecommerce.clothing.customer.utils;

import com.ecommerce.clothing.customer.model.entity.UserAccess;
import com.ecommerce.clothing.customer.repository.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidation {

    private final UserAccessRepository repository;

    public void validate(String userName, String password) {
        Optional<UserAccess> userAccessOptional = repository.findByUserNameAndPasswordAndActive(userName, password, true);
        if (userAccessOptional.isEmpty()) {
            throw new RuntimeException("user credentials not valid");
        }
    }
}
