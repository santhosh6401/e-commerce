package com.ecommerce.clothing.order.controller;

import com.ecommerce.clothing.order.model.response.CommonResponse;
import com.ecommerce.clothing.order.model.response.OrderAndPaymentResponse;
import com.ecommerce.clothing.order.service.OrderAndPaymentService;
import com.ecommerce.clothing.order.utils.UserValidation;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Order and Payment", value = "Order and Payment")
@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderAndPaymentController {

    private final UserValidation userValidation;

    private final OrderAndPaymentService orderAndPaymentService;

    @PostMapping("/payment/initiate")
    public OrderAndPaymentResponse initiateOrder(@RequestHeader String uniqueInteractionId,
                                                 @RequestHeader(name = "user-name") String userName,
                                                 @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : initiate order request", uniqueInteractionId);
        userValidation.validate(userName, password);
        return orderAndPaymentService.initiateOrder(uniqueInteractionId);
    }

    @PostMapping("/payment/complete")
    public OrderAndPaymentResponse paymentCompleted(@RequestHeader String uniqueInteractionId,
                                                 @RequestHeader(name = "user-name") String userName,
                                                 @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : initiate order request", uniqueInteractionId);
        userValidation.validate(userName, password);
        return orderAndPaymentService.paymentComplete(uniqueInteractionId);
    }

    @PostMapping("/delivery/status")
    public OrderAndPaymentResponse deliveryStatus(@RequestHeader String uniqueInteractionId,
                                                @RequestHeader(name = "user-name") String userName,
                                                @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}]  shipped order request", uniqueInteractionId);
        userValidation.validate(userName, password);
        return orderAndPaymentService.shipped(uniqueInteractionId);
    }



    @PutMapping
    public OrderAndPaymentResponse updateOrder(@RequestHeader String uniqueInteractionId,
                                               @RequestHeader(name = "user-name") String userName,
                                               @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] update the order request", uniqueInteractionId);
        userValidation.validate(userName, password);
        return orderAndPaymentService.updateOrder(uniqueInteractionId);
    }

    @DeleteMapping
    public CommonResponse deleteOrder(@RequestHeader String uniqueInteractionId,
                                      @RequestHeader(name = "user-name") String userName,
                                      @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] delete the order request", uniqueInteractionId);
        userValidation.validate(userName, password);
        return orderAndPaymentService.deleteOrder(uniqueInteractionId);
    }

    @GetMapping
    public OrderAndPaymentResponse getOrder(@RequestHeader String uniqueInteractionId,
                                            @RequestHeader(name = "user-name") String userName,
                                            @RequestHeader(name = "password") String password,
                                            @RequestParam(name = "order-id", required = false) String orderId,
                                            @RequestParam(name = "payment-id", required = false) String paymentId,
                                            @RequestParam(name = "payment-status", required = false) String paymentStatus,
                                            @RequestParam(name = "order-status", required = false) String orderStatus,
                                            @RequestParam(name = "delivery-status", required = false) String deliveryStatus
    ) throws Exception {
        log.info("interactionId :: [{}]  get order request", uniqueInteractionId);
        userValidation.validate(userName, password);
        return orderAndPaymentService.getOrder(uniqueInteractionId);
    }

}
