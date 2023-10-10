package com.ecommerce.clothing.customer.service;

import com.ecommerce.clothing.customer.constant.AppConstant;
import com.ecommerce.clothing.customer.model.entity.ComplaintsEntity;
import com.ecommerce.clothing.customer.model.request.complaint.ComplaintRequest;
import com.ecommerce.clothing.customer.model.response.CommonResponse;
import com.ecommerce.clothing.customer.model.response.complaint.Complaint;
import com.ecommerce.clothing.customer.model.response.complaint.ComplaintResponse;
import com.ecommerce.clothing.customer.repository.ComplaintsRepository;
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
public class ComplaintsService {

    private final HelperUtils utils;

    private final ComplaintsRepository complaintsRepository;

    private final MongoTemplate mongoTemplate;

    public CommonResponse createComplaints(ComplaintRequest request, String uniqueInteractionId) {
        CommonResponse response = new CommonResponse();
        if (Objects.isNull(request.getName()) ||
                Objects.isNull(request.getEmail()) ||
                Objects.isNull(request.getPhoneNo()) ||
                Objects.isNull(request.getMessage())) {
            response.setResponse(AppConstant.FAILED + " request all fields are mandatory ....");
            return response;
        }

        try {
            ComplaintsEntity complaints = new ComplaintsEntity();
            complaints.setId(utils.generateId("CI"));
            BeanUtils.copyProperties(request, complaints);
            complaints.setAudit(utils.createAudit(uniqueInteractionId));
            complaintsRepository.save(complaints);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + " " + e.getMessage());
        }
        return response;
    }

    public ComplaintResponse getComplaints(String email, String phoneNo, String name) {
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

        List<ComplaintsEntity> complaints = mongoTemplate.find(query, ComplaintsEntity.class);

        if (complaints.isEmpty()) {
            return ComplaintResponse.builder()
                    .complaints(new ArrayList<>())
                    .response(AppConstant.FAILED + " no record found")
                    .build();
        }

        List<Complaint> complaintList = new ArrayList<>();

        for (ComplaintsEntity complaintsEntity : complaints) {
            Complaint complaint = new Complaint();
            BeanUtils.copyProperties(complaintsEntity, complaint);
            complaintList.add(complaint);
        }

        return ComplaintResponse.builder()
                .complaints(complaintList)
                .response(AppConstant.SUCCESS)
                .build();
    }
}
