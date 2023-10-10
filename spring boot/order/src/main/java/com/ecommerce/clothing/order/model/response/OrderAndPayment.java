package com.ecommerce.clothing.order.model.response;


import com.ecommerce.clothing.order.model.common.Address;
import com.ecommerce.clothing.order.model.common.Delivery;
import com.ecommerce.clothing.order.model.common.Payment;
import com.ecommerce.clothing.order.model.common.ProductDetails;
import com.ecommerce.clothing.order.model.enums.DeliveryStatus;
import com.ecommerce.clothing.order.model.enums.OrderStatus;
import com.ecommerce.clothing.order.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAndPayment {

    private String orderId;
    private String orderName;
    private String orderNotes;
    private Address address;

    private ProductDetails productDetails;

    private Payment payment;
    private PaymentStatus paymentStatus;                /* after complete the payment gateway*/

    private LocalDateTime orderCreatedTime;
    private OrderStatus orderStatus;                   /* after checkout */

    private Delivery delivery;                        /* after send to delivery service */
    private DeliveryStatus deliveryStatus;

}
