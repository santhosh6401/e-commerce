package com.ecommerce.clothing.customer.model.cart;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CartModel {
    private List<String> productIds;
    private Map<String, String> quantity;
}
