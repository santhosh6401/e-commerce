package com.ecommerce.clothing.order.model.entity;

import com.ecommerce.clothing.order.model.common.*;
import com.ecommerce.clothing.order.model.enums.DeliveryStatus;
import com.ecommerce.clothing.order.model.enums.OrderStatus;
import com.ecommerce.clothing.order.model.enums.PaymentStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "order_payment")
public class OrderAndPaymentEntity {
    @Id
    private String id;
    @Indexed(unique = true)
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

    private List<LifeCycle> lifeCycles = new ArrayList<>();
    private Audit audit;
}
