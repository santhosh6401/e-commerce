package com.ecommerce.clothing.customer.service;

import com.ecommerce.clothing.customer.constant.AppConstant;
import com.ecommerce.clothing.customer.model.entity.ProfileEntity;
import com.ecommerce.clothing.customer.model.request.profile.PasswordResetRequest;
import com.ecommerce.clothing.customer.model.request.profile.ProfileRequest;
import com.ecommerce.clothing.customer.model.request.profile.SignInRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.profile.Profile;
import com.ecommerce.clothing.customer.model.response.profile.ProfileResponse;
import com.ecommerce.clothing.customer.repository.ProfileRepository;
import com.ecommerce.clothing.customer.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final HelperUtils utils;

    private final ProfileRepository profileRepository;

    private final MongoTemplate mongoTemplate;

    public ProfileResponse signUp(ProfileRequest request, String uniqueInteractionId) {

        ProfileResponse response = new ProfileResponse();

        if (Objects.isNull(request.getFirstName()) ||
                Objects.isNull(request.getLastName()) ||
                Objects.isNull(request.getEmail()) ||
                Objects.isNull(request.getPhoneNo()) ||
                Objects.isNull(request.getPassword())) {
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }

        Optional<ProfileEntity> profileOptional = profileRepository.findByEmail(request.getEmail());

        if (profileOptional.isPresent()) {
            response.setProfiles(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + " email id already registered ....");
            return response;
        }
        try {
            ProfileEntity profile = new ProfileEntity();
            profile.setProfileId(utils.generateId("CP"));
            BeanUtils.copyProperties(request, profile);
            profile.setPassword(utils.passwordEncode(request.getPassword()));
            profile.setLifeCycles(utils.upsertLifeCycles("profile created ", profile.getLifeCycles()));
            profile.setAudit(utils.createAudit(uniqueInteractionId));
            profileRepository.save(profile);
            Profile customerOnBoard = new Profile();
            BeanUtils.copyProperties(profile, customerOnBoard);
            response.setProfiles(Collections.singletonList(customerOnBoard));
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setProfiles(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + " " + e.getMessage());

        }
        return response;
    }

    public ProfileResponse signIn(SignInRequest request) {

        ProfileResponse response = new ProfileResponse();

        if (Objects.isNull(request.getEmail()) ||
                Objects.isNull(request.getPassword())) {
            response.setProfiles(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }

        Optional<ProfileEntity> profileOptional = profileRepository.findByEmailAndPassword(request.getEmail(), utils.passwordEncode(request.getPassword()));

        if (profileOptional.isEmpty()) {
            response.setProfiles(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + " invalid email ( or ) password ....");
            return response;
        }

        Profile profile = new Profile();
        BeanUtils.copyProperties(profileOptional.get(), profile);
        response.setProfiles(Collections.singletonList(profile));
        response.setResponse(AppConstant.SUCCESS);
        return response;

    }

    public CommonResponse passwordReset(PasswordResetRequest request) {

        CommonResponse response = new CommonResponse();

        if (Objects.isNull(request.getEmail()) ||
                Objects.isNull(request.getPassword()) ||
                Objects.isNull(request.getKey())
        ) {
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }

        Optional<ProfileEntity> profileOptional = profileRepository.findByEmailAndPasswordResetKey(request.getEmail(), request.getKey());

        if (profileOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + " invalid email ( or ) password ....");
            return response;
        }
        try {
            ProfileEntity profile = profileOptional.get();
            profile.setPassword(utils.passwordEncode(request.getPassword()));
            profile.setLifeCycles(utils.upsertLifeCycles("password reset ", profile.getLifeCycles()));
            profileRepository.save(profile);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED);
        }
        return response;

    }


    public CommonResponse passwordResetKeyGenerateAndSend(String email) {

        CommonResponse response = new CommonResponse();

        if (Objects.isNull(email)) {
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }

        Optional<ProfileEntity> profileOptional = profileRepository.findByEmail(email);

        if (profileOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + " email id not presented in our system  ....");
            return response;
        }

        /* key auto generate and save  */
        try {
            ProfileEntity profile = profileOptional.get();
            profile.setPasswordResetKey(utils.autoGenerateKey(email));
            profile.setLifeCycles(utils.upsertLifeCycles("password reset key send through email ", profile.getLifeCycles()));
            profileRepository.save(profile);
            response.setResponse(AppConstant.SUCCESS);

            /* TODO send mail is pending */

        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + " " + e.getMessage());
        }
        return response;

    }

    public ProfileResponse getUsers(String email, String phoneNo, String firstName, String lastName, int limit) {

        Query query = new Query();
        if (Objects.nonNull(email)) {
            query.addCriteria(Criteria.where("email").is(email));
        }
        if (Objects.nonNull(phoneNo)) {
            query.addCriteria(Criteria.where("phoneNo").is(phoneNo));
        }
        if (Objects.nonNull(firstName)) {
            query.addCriteria(Criteria.where("firstName").is(firstName));
        }
        if (Objects.nonNull(lastName)) {
            query.addCriteria(Criteria.where("lastName").is(lastName));
        }
        if (limit > 0)
            query.limit(limit);

        List<ProfileEntity> profiles = mongoTemplate.find(query, ProfileEntity.class);

        if (profiles.isEmpty()) {
            return ProfileResponse.builder()
                    .profiles(new ArrayList<>())
                    .response(AppConstant.FAILED + " no record found")
                    .build();
        }

        List<Profile> customerOnBoards = new ArrayList<>();

        for (ProfileEntity profile : profiles) {
            Profile cutomer = new Profile();
            BeanUtils.copyProperties(profile, cutomer);
            customerOnBoards.add(cutomer);
        }
        return ProfileResponse.builder()
                .profiles(customerOnBoards)
                .response(AppConstant.SUCCESS)
                .build();
    }
}
