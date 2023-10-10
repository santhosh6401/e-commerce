package com.ecommerce.clothing.customer.controller;

import com.ecommerce.clothing.customer.model.request.profile.PasswordResetRequest;
import com.ecommerce.clothing.customer.model.request.profile.ProfileRequest;
import com.ecommerce.clothing.customer.model.request.profile.SignInRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.profile.ProfileResponse;
import com.ecommerce.clothing.customer.service.ProfileService;
import com.ecommerce.clothing.customer.utils.UserValidation;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "On-Boarding", value = "On-Boarding")
@Slf4j
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final UserValidation userValidation;

    @PostMapping("/signup")
    public ProfileResponse signup(@RequestHeader String uniqueInteractionId,
                                  @RequestBody ProfileRequest request,
                                  @RequestHeader(name = "user-name") String userName,
                                  @RequestHeader(name = "password") String password) {
        log.info("interactionId :: [{}] request : {} sign up ", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return profileService.signUp(request, uniqueInteractionId);
    }


    @PostMapping("/signIn")
    public ProfileResponse signIn(@RequestHeader String uniqueInteractionId,
                                  @RequestBody SignInRequest request,
                                  @RequestHeader(name = "user-name") String userName,
                                  @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} sign in ", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return profileService.signIn(request);
    }

    @PutMapping("/password/reset")
    public CommonResponse passwordUpdate(@RequestHeader String uniqueInteractionId,
                                         @RequestHeader(name = "user-name") String userName,
                                         @RequestHeader(name = "password") String password,
                                         @RequestBody PasswordResetRequest request) throws Exception {
        log.info("interactionId :: [{}] request : {} password reset", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return profileService.passwordReset(request);
    }

    @PutMapping("/password/send-key")
    public CommonResponse passwordResetKeyGenerateAndSend(@RequestHeader String uniqueInteractionId,
                                                          @RequestHeader(name = "user-name") String userName,
                                                          @RequestHeader(name = "password") String password,
                                                          @RequestParam String email) throws Exception {
        log.info("interactionId :: [{}] request : {} password reset key generate", uniqueInteractionId, email);
        userValidation.validate(userName, password);
        return profileService.passwordResetKeyGenerateAndSend(email);
    }

    @GetMapping
    public ProfileResponse getUsers(@RequestHeader String uniqueInteractionId,
                                    @RequestHeader(name = "user-name") String userName,
                                    @RequestHeader(name = "password") String password,
                                    @RequestParam(name = "email", required = false) String email,
                                    @RequestParam(name = "phone-no", required = false) String phoneNo,
                                    @RequestParam(name = "first-name", required = false) String firstName,
                                    @RequestParam(name = "last-name", required = false) String lastName,
                                    @RequestParam(name = "limit", required = false) int limit
    ) throws Exception {
        log.info("interactionId :: [{}] request for get profile details , email {} , phoneNo : {} firstName : {} lastName : {} limit : {}", uniqueInteractionId, email, phoneNo, firstName, lastName, limit);
        userValidation.validate(userName, password);
        return profileService.getUsers(email, phoneNo, firstName, lastName, limit);
    }
}
