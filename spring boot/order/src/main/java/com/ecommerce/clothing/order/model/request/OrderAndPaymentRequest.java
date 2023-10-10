package com.ecommerce.clothing.order.model.request;

import com.ecommerce.clothing.order.model.common.Address;
import com.ecommerce.clothing.order.model.common.ProductDetails;
import lombok.Data;

@Data
public class OrderAndPaymentRequest {
    private String orderName;
    private String orderNotes;
    private Address address;
    private ProductDetails productDetails;
}

