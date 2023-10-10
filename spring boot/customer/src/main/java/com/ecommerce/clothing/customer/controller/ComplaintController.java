package com.ecommerce.clothing.customer.controller;

import com.ecommerce.clothing.customer.model.request.complaint.ComplaintRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.complaint.ComplaintResponse;
import com.ecommerce.clothing.customer.service.ComplaintsService;
import com.ecommerce.clothing.customer.utils.UserValidation;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Complaints", value = "Complaints")
@Slf4j
@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintsService complaintsService;
    private final UserValidation userValidation;

    @PostMapping
    public CommonResponse createComplaints(@RequestHeader String uniqueInteractionId,
                                           @RequestBody ComplaintRequest request,
                                           @RequestHeader(name = "user-name") String userName,
                                           @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} create complaints ", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return complaintsService.createComplaints(request, uniqueInteractionId);
    }

    @GetMapping
    public ComplaintResponse getComplaints(@RequestHeader String uniqueInteractionId,
                                           @RequestHeader(name = "user-name") String userName,
                                           @RequestHeader(name = "password") String password,
                                           @RequestParam(name = "email", required = false) String email,
                                           @RequestParam(name = "phone-no", required = false) String phoneNo,
                                           @RequestParam(name = "name", required = false) String name
    ) throws Exception {
        log.info("interactionId :: [{}] request for get complaints details , email {} , phoneNo : {} name : {}", uniqueInteractionId, email, phoneNo, name);
        userValidation.validate(userName, password);
        return complaintsService.getComplaints(email, phoneNo, name);
    }
}
