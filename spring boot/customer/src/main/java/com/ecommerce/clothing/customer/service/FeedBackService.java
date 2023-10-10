package com.ecommerce.clothing.customer.service;

import com.ecommerce.clothing.customer.constant.AppConstant;
import com.ecommerce.clothing.customer.model.entity.FeedbackEntity;
import com.ecommerce.clothing.customer.model.request.feedback.FeedbackRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.feedback.Feedback;
import com.ecommerce.clothing.customer.model.response.feedback.FeedbackResponse;
import com.ecommerce.clothing.customer.repository.FeedbackRepository;
import com.ecommerce.clothing.customer.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FeedBackService {

    private final HelperUtils utils;

    private final FeedbackRepository feedbackRepository;

    private final MongoTemplate mongoTemplate;

    public CommonResponse createFeedback(FeedbackRequest request, String uniqueInteractionId) {
        CommonResponse response = new CommonResponse();
        if (Objects.isNull(request.getName()) ||
                Objects.isNull(request.getEmail()) ||
                Objects.isNull(request.getPhoneNo()) ||
                Objects.isNull(request.getMessage())) {
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }

        try {
            FeedbackEntity feedBack = new FeedbackEntity();
            feedBack.setId(utils.generateId("FI"));
            BeanUtils.copyProperties(request, feedBack);
            feedBack.setAudit(utils.createAudit(uniqueInteractionId));
            feedbackRepository.save(feedBack);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + " " + e.getMessage());
        }

        return response;
    }

    public FeedbackResponse getFeedback(String email, String phoneNo, String name, int limit) {

        Query query = new Query();
        if (Objects.nonNull(email)) {
            query.addCriteria(Criteria.where("email").is(email));
        }
        if (Objects.nonNull(phoneNo)) {
            query.addCriteria(Criteria.where("phoneNo").is(phoneNo));
        }
        if (Objects.nonNull(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (limit > 0) {
            query.limit(limit);
        }

        List<FeedbackEntity> feedBacks = mongoTemplate.find(query, FeedbackEntity.class);

        if (feedBacks.isEmpty()) {
            return FeedbackResponse.builder()
                    .feedbacks(new ArrayList<>())
                    .response(AppConstant.FAILED + " no record found")
                    .build();
        }
        List<Feedback> feedbackList = new ArrayList<>();

        for (FeedbackEntity feedBackEntity : feedBacks) {
            Feedback feedback = new Feedback();
            BeanUtils.copyProperties(feedBackEntity, feedback);
            feedbackList.add(feedback);
        }

        return FeedbackResponse.builder()
                .feedbacks(feedbackList)
                .response(AppConstant.SUCCESS)
                .build();
    }
}
