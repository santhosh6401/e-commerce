package com.ecommerce.clothing.customer.controller;


import com.ecommerce.clothing.customer.model.request.feedback.FeedbackRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.feedback.FeedbackResponse;
import com.ecommerce.clothing.customer.service.FeedBackService;
import com.ecommerce.clothing.customer.utils.UserValidation;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Feedback", value = "Feedback")
@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedBackService feedBackService;

    private final UserValidation userValidation;

    @PostMapping
    public CommonResponse createFeedback(@RequestHeader String uniqueInteractionId,
                                         @RequestBody FeedbackRequest request,
                                         @RequestHeader(name = "user-name") String userName,
                                         @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {}  create feedback ", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return feedBackService.createFeedback(request, uniqueInteractionId);
    }

    @GetMapping
    public FeedbackResponse getFeedBack(@RequestHeader String uniqueInteractionId,
                                        @RequestHeader(name = "user-name") String userName,
                                        @RequestHeader(name = "password") String password,
                                        @RequestParam(name = "email", required = false) String email,
                                        @RequestParam(name = "phone-no", required = false) String phoneNo,
                                        @RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "limit", required = false) int limit
    ) throws Exception {
        log.info("interactionId :: [{}] request for get feedback details , email {} , phoneNo : {} name : {} limit: {} ", uniqueInteractionId, email, phoneNo, name, limit);
        userValidation.validate(userName, password);
        return feedBackService.getFeedback(email, phoneNo, name, limit);
    }


}
