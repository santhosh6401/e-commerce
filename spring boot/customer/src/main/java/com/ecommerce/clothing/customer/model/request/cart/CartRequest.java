package com.ecommerce.clothing.customer.model.request.cart;

import com.ecommerce.clothing.customer.model.cart.CartModel;
import com.ecommerce.clothing.customer.model.common.Amount;
import lombok.Data;

@Data
public class CartRequest {
    private String name;
    private String email;
    private String phoneNo;
    private CartModel cart;
    private Amount totalPrice;
}
