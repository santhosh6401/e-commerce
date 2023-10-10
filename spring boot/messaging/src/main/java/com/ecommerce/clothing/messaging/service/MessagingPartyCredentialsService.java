package com.ecommerce.clothing.messaging.service;

import com.ecommerce.clothing.messaging.constant.AppConstant;
import com.ecommerce.clothing.messaging.model.entity.MessagingPartyCredentialsConfig;
import com.ecommerce.clothing.messaging.model.enums.MessagingChannel;
import com.ecommerce.clothing.messaging.model.request.credentials.MessagingPartyCredentialsConfigRequest;
import com.ecommerce.clothing.messaging.model.response.CommonResponse;
import com.ecommerce.clothing.messaging.model.response.credentials.MessagingPartyCredentialConfigResponse;
import com.ecommerce.clothing.messaging.repository.MessagingPartyCredentialsConfigRepository;
import com.ecommerce.clothing.messaging.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MessagingPartyCredentialsService {

    private final MessagingPartyCredentialsConfigRepository repository;

    private final HelperUtils utils;


    private final MongoTemplate mongoTemplate;


    public MessagingPartyCredentialConfigResponse createTpCredentialConfig(MessagingPartyCredentialsConfigRequest request, String uniqueInteractionId) throws Exception {

        MessagingPartyCredentialConfigResponse response = new MessagingPartyCredentialConfigResponse();

        if (Objects.isNull(request.getName()) || Objects.isNull(request.getCredentials()) || Objects.isNull(request.getMessagingChannel())) {
            response.setResponse(AppConstant.FAILED + "  name | credentials | messaging channel is missing .... ");
            return response;
        }

        Optional<MessagingPartyCredentialsConfig> credentialsConfigOptional = repository.findByName(request.getName());

        if (credentialsConfigOptional.isPresent()) {
            response.setResponse(AppConstant.FAILED + "  credential is already exists .... ");
            return response;
        }

        BeanUtils.copyProperties(request, response);
        try {
            MessagingPartyCredentialsConfig credential = new MessagingPartyCredentialsConfig();
            credential.setId(utils.generateId("TP"));
            BeanUtils.copyProperties(request, credential);
            credential.setAudit(utils.createAudit(uniqueInteractionId));
            repository.save(credential);

            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;
    }

    public MessagingPartyCredentialConfigResponse updateTpCredentialConfig(MessagingPartyCredentialsConfigRequest request, String uniqueInteractionId) throws Exception {

        MessagingPartyCredentialConfigResponse response = new MessagingPartyCredentialConfigResponse();
        if (Objects.isNull(request.getName()) || Objects.isNull(request.getCredentials()) || Objects.isNull(request.getMessagingChannel())) {
            response.setResponse(AppConstant.FAILED + "  name | credentials | messaging channel is missing .... ");
            return response;
        }

        Optional<MessagingPartyCredentialsConfig> credentialsConfigOptional = repository.findByName(request.getName());

        if (credentialsConfigOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + "  credentials is not found .... ");
            return response;
        }

        BeanUtils.copyProperties(request, response);
        try {
            MessagingPartyCredentialsConfig credential = credentialsConfigOptional.get();
            BeanUtils.copyProperties(request, credential);
            credential.setAudit(utils.updateAudit(uniqueInteractionId, credential.getAudit()));
            repository.save(credential);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;

    }

    public CommonResponse deleteTpCredentialConfig(String name) throws Exception {

        CommonResponse response = new CommonResponse();

        if (Objects.isNull(name)) {
            response.setResponse(AppConstant.FAILED + "  name is missing .... ");
            return response;
        }

        Optional<MessagingPartyCredentialsConfig> credentialsConfigOptional = repository.findByName(name);
        if (credentialsConfigOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + "  credential is not found .... ");
            return response;
        }

        try {
            repository.delete(credentialsConfigOptional.get());
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;
    }

    public List<MessagingPartyCredentialConfigResponse> getTpCredentialConfigs(String name, MessagingChannel messagingChannel) throws Exception {

        Query query = new Query();
        if (Objects.nonNull(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (Objects.nonNull(messagingChannel)) {
            query.addCriteria(Criteria.where("messagingChannel").is(messagingChannel));
        }
        List<MessagingPartyCredentialsConfig> credentialsConfigList = mongoTemplate.find(query, MessagingPartyCredentialsConfig.class);

        if (credentialsConfigList.isEmpty()) {
            MessagingPartyCredentialConfigResponse response = MessagingPartyCredentialConfigResponse.builder()
                    .response(AppConstant.FAILED + " no record found")
                    .build();
            return Collections.singletonList(response);
        }

        List<MessagingPartyCredentialConfigResponse> responses = new ArrayList<>();

        for (MessagingPartyCredentialsConfig credentialsConfig : credentialsConfigList) {
            MessagingPartyCredentialConfigResponse response = new MessagingPartyCredentialConfigResponse();
            BeanUtils.copyProperties(credentialsConfig, response);
            responses.add(response);
        }
        return responses;
    }
}
