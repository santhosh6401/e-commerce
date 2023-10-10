package com.ecommerce.clothing.appearance.controller;


import com.ecommerce.clothing.appearance.model.request.AppearanceRequest;
import com.ecommerce.clothing.appearance.model.response.CommonResponse;
import com.ecommerce.clothing.appearance.model.response.appearance.AppearanceResponse;
import com.ecommerce.clothing.appearance.service.AppearanceService;
import com.ecommerce.clothing.appearance.utils.UserValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Appearance", value = "Appearance")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AppearanceController {
    private final UserValidation userValidation;

    private final AppearanceService appearanceService;

    @PostMapping
    public AppearanceResponse createAppearance(@RequestHeader String uniqueInteractionId,
                                               @RequestBody AppearanceRequest request,
                                               @RequestHeader(name = "user-name") String userName,
                                               @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} create the appearance request", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return appearanceService.createAppearance(request, uniqueInteractionId);
    }

    @PutMapping
    public AppearanceResponse updateAppearance(@RequestHeader String uniqueInteractionId,
                                               @RequestBody AppearanceRequest request,
                                               @RequestHeader(name = "user-name") String userName,
                                               @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} update the appearance request", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return appearanceService.updateAppearance(request, uniqueInteractionId);
    }

    @DeleteMapping
    public CommonResponse deleteAppearance(@RequestHeader String uniqueInteractionId,
                                           @RequestParam String name,
                                           @RequestHeader(name = "user-name") String userName,
                                           @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] content-name : {} delete the appearance", uniqueInteractionId, name);
        userValidation.validate(userName, password);
        return appearanceService.deleteAppearance(name);
    }

    @GetMapping
    public AppearanceResponse getAppearance(
            @RequestHeader String uniqueInteractionId,
            @RequestParam(required = false, value = "appearance-names", name = "appearance-names") @ApiParam(name = "appearance-names") String appearanceNames,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] appearance-name : {} get the config", uniqueInteractionId, appearanceNames);
        userValidation.validate(userName, password);
        return appearanceService.getAppearance(appearanceNames);
    }

    @PostMapping("/upload-img")
    public CommonResponse appearanceImageUpsert(@RequestHeader String uniqueInteractionId,
                                                @RequestParam String imageUrl,
                                                @RequestParam String appearanceName,
                                                @RequestHeader(name = "user-name") String userName,
                                                @RequestHeader(name = "password") String password) throws Exception {

        log.info("interactionId :: [{}] and product id : {} upsert image the product ", uniqueInteractionId, appearanceName);
        userValidation.validate(userName, password);
        return appearanceService.appearanceImageUpsert(imageUrl, appearanceName, uniqueInteractionId);
    }


}
