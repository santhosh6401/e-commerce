package com.ecommerce.clothing.messaging.controller;


import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import com.ecommerce.clothing.messaging.model.request.messagecontent.MessageContentConfigRequest;
import com.ecommerce.clothing.messaging.model.response.CommonResponse;
import com.ecommerce.clothing.messaging.model.response.messagecontent.MessageContentConfigResponse;
import com.ecommerce.clothing.messaging.service.MessageContentConfigService;
import com.ecommerce.clothing.messaging.utils.UserValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Message Content Configuration", value = "Message Content Configuration")
@Slf4j
@RestController
@RequestMapping("/message-content")
public class MessageContentConfigController {

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private MessageContentConfigService messageContentConfigService;

    @PostMapping
    public MessageContentConfigResponse createMessageContentConfig(@RequestHeader String uniqueInteractionId,
                                                                   @RequestBody MessageContentConfigRequest request,
                                                                   @RequestHeader(name = "user-name") String userName,
                                                                   @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} create the content config", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return messageContentConfigService.createMessageContent(request, uniqueInteractionId);
    }

    @PutMapping
    public MessageContentConfigResponse updateMessageContentConfig(@RequestHeader String uniqueInteractionId,
                                                                   @RequestBody MessageContentConfigRequest request,
                                                                   @RequestHeader(name = "user-name") String userName,
                                                                   @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} update the content config", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return messageContentConfigService.updateMessageContent(request, uniqueInteractionId);
    }

    @DeleteMapping
    public CommonResponse deleteMessageContentConfig(@RequestHeader String uniqueInteractionId,
                                                     @RequestParam String name,
                                                     @RequestHeader(name = "user-name") String userName,
                                                     @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] content-name : {} delete the content config", uniqueInteractionId, name);
        userValidation.validate(userName, password);
        return messageContentConfigService.deleteMessageContent(name);
    }

    @GetMapping
    public List<MessageContentConfigResponse> getMessageContentConfig(
            @RequestHeader String uniqueInteractionId,
            @RequestParam(required = false, value = "content-name", name = "content-name") @ApiParam(name = "content-name") String name,
            @RequestParam(required = false) MessagingChannel messagingChannel,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] content-name : {} channel : {} get the content config", uniqueInteractionId, name, messagingChannel);
        userValidation.validate(userName, password);
        return messageContentConfigService.getMessageContent(name, messagingChannel);
    }
}
