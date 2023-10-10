package com.ecommerce.clothing.messaging.controller;

import com.ecommerce.clothing.messaging.model.request.messagecontent.DynamicValueRequest;
import com.ecommerce.clothing.messaging.model.response.CommonResponse;
import com.ecommerce.clothing.messaging.service.MessagingService;
import com.ecommerce.clothing.messaging.utils.UserValidation;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Messaging", value = "Messaging")
@Slf4j
@RestController
@RequestMapping
public class MessagingController {

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private MessagingService messagingService;

    @PostMapping("/telegram")
    public CommonResponse sendTelegramMessage(@RequestHeader String uniqueInteractionId,
                                              @RequestParam(name = "credential-name") String messagingCredentialName,
                                              @RequestParam(name = "content-name", required = false) String messagingContentName,
                                              @RequestHeader(name = "user-name") String userName,
                                              @RequestHeader(name = "password") String password,
                                              @RequestParam(name = "custom-message", required = false) String customMessage,
                                              @RequestBody(required = false) DynamicValueRequest dynamicValues) throws Exception {
        log.info("interactionId :: [{}] credentialName : {} content-name : {} customMessage : {} ", uniqueInteractionId, messagingCredentialName, messagingContentName, customMessage);
        userValidation.validate(userName, password);
        return messagingService.sendTextMessage(messagingCredentialName, messagingContentName, customMessage, dynamicValues);
    }

    @PostMapping("/gmail")
    public CommonResponse sendGmail(@RequestHeader String uniqueInteractionId,
                                    @RequestParam(name = "credential-name") String messagingCredentialName,
                                    @RequestParam(name = "content-name", required = false) String messagingContentName,
                                    @RequestParam(name = "receiver-mail") String to,
                                    @RequestHeader(name = "user-name") String userName,
                                    @RequestHeader(name = "password") String password,
                                    @RequestParam(name = "subject", required = false) String subject,
                                    @RequestParam(name = "body", required = false) String body,
                                    @RequestBody(required = false) DynamicValueRequest dynamicValues) throws Exception {
        log.info("interactionId :: [{}] credentialName : {} content-name : {} receiver : {} subject : {} body : {} ", uniqueInteractionId, messagingCredentialName, messagingContentName, to, subject, body);
        userValidation.validate(userName, password);
        return messagingService.sendGmail(messagingCredentialName, messagingContentName, to, subject, body, dynamicValues);
    }
}
