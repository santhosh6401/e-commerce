package com.ecommerce.clothing.customer.service;

import com.ecommerce.clothing.customer.constant.AppConstant;
import com.ecommerce.clothing.customer.model.entity.FAQsEntity;
import com.ecommerce.clothing.customer.model.request.faqs.FAQsRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.faqs.FAQs;
import com.ecommerce.clothing.customer.model.response.faqs.FAQsResponse;
import com.ecommerce.clothing.customer.repository.FAQsRepository;
import com.ecommerce.clothing.customer.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FAQsService {

    private final HelperUtils utils;

    private final FAQsRepository faQsRepository;

    private final MongoTemplate mongoTemplate;

    public CommonResponse createFaqs(FAQsRequest request, String uniqueInteractionId) {

        CommonResponse response = new CommonResponse();
        if (Objects.isNull(request.getQuery()) ||
                Objects.isNull(request.getAnswer())) {
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }

        Optional<FAQsEntity> faQsOptional = faQsRepository.findById(request.getId());

        if (faQsOptional.isPresent()) {
            response.setResponse(AppConstant.FAILED + " FAQ id already exist ...");
            return response;
        }

        try {
            FAQsEntity faQs = new FAQsEntity();
            BeanUtils.copyProperties(request, faQs);
            faQs.setAudit(utils.createAudit(uniqueInteractionId));
            faQsRepository.save(faQs);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + " " + e.getMessage());
        }
        return response;
    }

    public FAQsResponse getFaqs(int limit) {

        Query query = new Query();
        if (limit > 0) {
            query.limit(limit);
        }

        List<FAQsEntity> faQs = mongoTemplate.find(query, FAQsEntity.class);

        if (faQs.isEmpty()) {
            return FAQsResponse.builder()
                    .faQs(new ArrayList<>())
                    .response(AppConstant.FAILED + " no record found")
                    .build();
        }

        List<FAQs> faQsList = new ArrayList<>();

        for (FAQsEntity faQsEntity : faQs) {
            FAQs faQ = new FAQs();
            BeanUtils.copyProperties(faQsEntity, faQ);
            faQsList.add(faQ);
        }

        return FAQsResponse.builder()
                .faQs(faQsList)
                .response(AppConstant.SUCCESS)
                .build();
    }
}
