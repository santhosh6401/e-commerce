package com.ecommerce.clothing.customer.service;

import com.ecommerce.clothing.customer.constant.AppConstant;
import com.ecommerce.clothing.customer.model.entity.CartEntity;
import com.ecommerce.clothing.customer.model.entity.ProfileEntity;
import com.ecommerce.clothing.customer.model.request.cart.CartRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.cart.Cart;
import com.ecommerce.clothing.customer.model.response.cart.CartResponse;
import com.ecommerce.clothing.customer.repository.CartRepository;
import com.ecommerce.clothing.customer.repository.ProfileRepository;
import com.ecommerce.clothing.customer.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final HelperUtils utils;

    private final CartRepository cartRepository;

    private final ProfileRepository profileRepository;

    private final MongoTemplate mongoTemplate;

    public CommonResponse upsertCart(CartRequest request, String uniqueInteractionId) {
        CommonResponse response = new CommonResponse();

        if (Objects.isNull(request.getEmail()) ||
                Objects.isNull(request.getPhoneNo()) ||
                Objects.isNull(request.getCart())) {
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }
        Optional<ProfileEntity> profileOptional = profileRepository.findByEmail(request.getEmail());

        if (profileOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + " customer account not created ...");
            return response;
        }

        CartEntity cart;
        Optional<CartEntity> cartOptional = cartRepository.findByEmailAndPhoneNoAndName(request.getEmail(), request.getPhoneNo(), request.getName());
        try {
            if (cartOptional.isPresent()) {
                cart = cartOptional.get();
                BeanUtils.copyProperties(request, cart);
                cart.setAudit(utils.updateAudit(uniqueInteractionId, cart.getAudit()));
                cart.setLifeCycles(utils.upsertLifeCycles("cart updated ", cart.getLifeCycles()));
                cartRepository.save(cart);
                response.setResponse(AppConstant.SUCCESS + " cart updated...");
            } else {
                cart = new CartEntity();
                cart.setId(utils.generateId("CCI"));
                BeanUtils.copyProperties(request, cart);
                cart.setAudit(utils.createAudit(uniqueInteractionId));
                cart.setLifeCycles(utils.upsertLifeCycles("cart created ", cart.getLifeCycles()));
                cartRepository.save(cart);
                response.setResponse(AppConstant.SUCCESS + " cart created");
            }

        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + " " + e.getMessage());
        }
        return response;
    }

    public CartResponse getCart(String email, String phoneNo, String name) {
        Query query = new Query();
        if (Objects.nonNull(email)) {
            query.addCriteria(Criteria.where("email").is(email));
        }
        if (Objects.nonNull(phoneNo)) {
            query.addCriteria(Criteria.where("phoneNo").is(phoneNo));
        }
        if (Objects.nonNull(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }

        List<CartEntity> carts = mongoTemplate.find(query, CartEntity.class);

        if (carts.isEmpty()) {
            return CartResponse.builder()
                    .carts(new ArrayList<>())
                    .response(AppConstant.FAILED + " no record found")
                    .build();
        }

        List<Cart> cartList = new ArrayList<>();

        for (CartEntity cartEntity : carts) {
            Cart cart = new Cart();
            BeanUtils.copyProperties(cartEntity, cart);
            cartList.add(cart);
        }

        return CartResponse.builder()
                .carts(cartList)
                .response(AppConstant.SUCCESS)
                .build();
    }

}
