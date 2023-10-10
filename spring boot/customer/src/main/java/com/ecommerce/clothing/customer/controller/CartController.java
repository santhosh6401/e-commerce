package com.ecommerce.clothing.customer.controller;

import com.ecommerce.clothing.customer.model.request.cart.CartRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.cart.CartResponse;
import com.ecommerce.clothing.customer.service.CartService;
import com.ecommerce.clothing.customer.utils.UserValidation;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Cart", value = "Cart")
@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final UserValidation userValidation;

    @PostMapping
    public CommonResponse upsertCart(@RequestHeader String uniqueInteractionId,
                                     @RequestBody CartRequest request,
                                     @RequestHeader(name = "user-name") String userName,
                                     @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} sign in ", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return cartService.upsertCart(request, uniqueInteractionId);
    }

    @GetMapping
    public CartResponse getCart(@RequestHeader String uniqueInteractionId,
                                @RequestHeader(name = "user-name") String userName,
                                @RequestHeader(name = "password") String password,
                                @RequestParam(name = "email", required = false) String email,
                                @RequestParam(name = "phone-no", required = false) String phoneNo,
                                @RequestParam(name = "name", required = false) String name
    ) throws Exception {
        log.info("interactionId :: [{}] request for get profile details , email {} , phoneNo : {} name : {} ", uniqueInteractionId, email, phoneNo, name);
        userValidation.validate(userName, password);
        return cartService.getCart(email, phoneNo, name);
    }

}
