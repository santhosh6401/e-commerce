package com.ecommerce.clothing.order.utils;


import com.ecommerce.clothing.order.model.entity.UserAccess;
import com.ecommerce.clothing.order.repository.UserAccessRepository;
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
