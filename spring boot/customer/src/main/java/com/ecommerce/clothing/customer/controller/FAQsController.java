package com.ecommerce.clothing.customer.controller;

import com.ecommerce.clothing.customer.model.request.faqs.FAQsRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.faqs.FAQsResponse;
import com.ecommerce.clothing.customer.service.FAQsService;
import com.ecommerce.clothing.customer.utils.UserValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/faq")
@RestController
@RequiredArgsConstructor
public class FAQsController {


    private final FAQsService faQsService;
    private final UserValidation userValidation;

    @PostMapping
    public CommonResponse createFaqs(@RequestHeader String uniqueInteractionId,
                                     @RequestBody FAQsRequest request,
                                     @RequestHeader(name = "user-name") String userName,
                                     @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} create FAQ ", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return faQsService.createFaqs(request, uniqueInteractionId);
    }

    @GetMapping
    public FAQsResponse getFaqs(@RequestHeader String uniqueInteractionId,
                                @RequestHeader(name = "user-name") String userName,
                                @RequestHeader(name = "password") String password,
                                @RequestParam(name = "limit", required = false) int limit
    ) throws Exception {
        log.info("interactionId :: [{}] request for get FAQ ", uniqueInteractionId);
        userValidation.validate(userName, password);
        return faQsService.getFaqs(limit);
    }

}
