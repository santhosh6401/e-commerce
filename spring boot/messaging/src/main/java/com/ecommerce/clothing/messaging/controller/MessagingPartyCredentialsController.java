package com.ecommerce.clothing.messaging.controller;

import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import com.ecommerce.clothing.messaging.model.request.credentials.MessagingPartyCredentialsConfigRequest;
import com.ecommerce.clothing.messaging.model.response.CommonResponse;
import com.ecommerce.clothing.messaging.model.response.credentials.MessagingPartyCredentialConfigResponse;
import com.ecommerce.clothing.messaging.service.MessagingPartyCredentialsService;
import com.ecommerce.clothing.messaging.utils.UserValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Messaging Channel Credentials", value = "Messaging Channel Credentials")
@Slf4j
@RestController
@RequestMapping("/credentials")
public class MessagingPartyCredentialsController {

    @Autowired
    private UserValidation userValidation;
    @Autowired
    private MessagingPartyCredentialsService messagingPartyCredentialsService;

    @PostMapping
    public MessagingPartyCredentialConfigResponse createTpCredentialConfig(@RequestHeader String uniqueInteractionId,
                                                                           @RequestBody MessagingPartyCredentialsConfigRequest request,
                                                                           @RequestHeader(name = "user-name") String userName,
                                                                           @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} create the credentials config", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return messagingPartyCredentialsService.createTpCredentialConfig(request, uniqueInteractionId);
    }

    @PutMapping
    public MessagingPartyCredentialConfigResponse updateTpCredentialConfig(@RequestHeader String uniqueInteractionId,
                                                                           @RequestBody MessagingPartyCredentialsConfigRequest request,
                                                                           @RequestHeader(name = "user-name") String userName,
                                                                           @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} update the credential config", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return messagingPartyCredentialsService.updateTpCredentialConfig(request, uniqueInteractionId);
    }

    @DeleteMapping
    public CommonResponse deleteTpCredentialConfig(@RequestHeader String uniqueInteractionId,
                                                   @RequestParam String name,
                                                   @RequestHeader(name = "user-name") String userName,
                                                   @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] content-name : {} delete the credential config", uniqueInteractionId, name);
        userValidation.validate(userName, password);
        return messagingPartyCredentialsService.deleteTpCredentialConfig(name);
    }

    @GetMapping
    public List<MessagingPartyCredentialConfigResponse> getTpCredentialConfigs(
            @RequestHeader String uniqueInteractionId,
            @RequestParam(required = false, value = "credential-name", name = "credential-name") @ApiParam(name = "credential-name") String name,
            @RequestParam(required = false) MessagingChannel messagingChannel,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] content-name : {} channel : {} get the credentials config", uniqueInteractionId, name, messagingChannel);
        userValidation.validate(userName, password);
        return messagingPartyCredentialsService.getTpCredentialConfigs(name, messagingChannel);
    }
}
