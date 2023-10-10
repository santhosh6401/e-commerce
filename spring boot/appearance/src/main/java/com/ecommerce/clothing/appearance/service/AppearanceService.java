package com.ecommerce.clothing.appearance.service;

import com.ecommerce.clothing.appearance.constant.AppConstant;
import com.ecommerce.clothing.appearance.model.entity.AppearanceEntity;
import com.ecommerce.clothing.appearance.model.request.AppearanceRequest;
import com.ecommerce.clothing.appearance.model.response.CommonResponse;
import com.ecommerce.clothing.appearance.model.response.appearance.Appearance;
import com.ecommerce.clothing.appearance.model.response.appearance.AppearanceResponse;
import com.ecommerce.clothing.appearance.repository.AppearanceRepository;
import com.ecommerce.clothing.appearance.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppearanceService {

    private final AppearanceRepository repository;

    private final HelperUtils utils;

    private final MongoTemplate mongoTemplate;


    public AppearanceResponse createAppearance(AppearanceRequest request, String uniqueInteractionId) {

        AppearanceResponse response = new AppearanceResponse();

        Optional<AppearanceEntity> appearanceOptional = repository.findByAppearanceName(request.getAppearanceName());

        if (appearanceOptional.isPresent()) {
            response.setAppearances(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + "  appearance is already exists .... ");
            return response;
        }
        Appearance appearance = new Appearance();
        try {
            AppearanceEntity appearanceEntity = new AppearanceEntity();
            appearanceEntity.setId(utils.generateId("AP"));
            BeanUtils.copyProperties(request, appearanceEntity);
            appearanceEntity.setAudit(utils.createAudit(uniqueInteractionId));
            appearanceEntity.setLifeCycles(utils.upsertLifeCycles("appearance created ", appearanceEntity.getLifeCycles()));
            repository.save(appearanceEntity);
            BeanUtils.copyProperties(appearanceEntity, appearance);
            List<Appearance> appearances = Collections.singletonList(appearance);
            response.setAppearances(appearances);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setAppearances(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;
    }

    public AppearanceResponse updateAppearance(AppearanceRequest request, String uniqueInteractionId) {

        AppearanceResponse response = new AppearanceResponse();

        Optional<AppearanceEntity> appearanceOptional = repository.findByAppearanceName(request.getAppearanceName());

        if (appearanceOptional.isEmpty()) {
            response.setAppearances(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + "  appearance is not found .... ");
            return response;
        }

        Appearance appearance = new Appearance();
        try {
            AppearanceEntity appearanceEntity = appearanceOptional.get();
            BeanUtils.copyProperties(request, appearanceEntity);
            appearanceEntity.setAudit(utils.updateAudit(uniqueInteractionId, appearanceEntity.getAudit()));
            appearanceEntity.setLifeCycles(utils.upsertLifeCycles("appearance updated", appearanceEntity.getLifeCycles()));
            repository.save(appearanceEntity);
            BeanUtils.copyProperties(appearanceEntity, appearance);
            List<Appearance> appearances = Collections.singletonList(appearance);
            response.setAppearances(appearances);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setAppearances(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;
    }

    public CommonResponse deleteAppearance(String name) {
        CommonResponse response = new CommonResponse();

        if (Objects.isNull(name)) {
            response.setResponse(AppConstant.FAILED + "  appearance is missing .... ");
            return response;
        }

        Optional<AppearanceEntity> appearanceOptional = repository.findByAppearanceName(name);
        if (appearanceOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + "  appearance not found .... ");
            return response;
        }

        try {
            repository.delete(appearanceOptional.get());
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;
    }

    public AppearanceResponse getAppearance(String appearanceNames) {

        Query query = new Query();
        if (Objects.nonNull(appearanceNames)) {
            List<String> appearanceNameList = List.of(appearanceNames.split(","));
            query.addCriteria(Criteria.where("name").in(appearanceNameList));
        }

        List<AppearanceEntity> appearanceEntities = mongoTemplate.find(query, AppearanceEntity.class);

        if (appearanceEntities.isEmpty()) {
            return AppearanceResponse.builder()
                    .appearances(new ArrayList<>())
                    .response(AppConstant.FAILED + " no record found")
                    .build();
        }

        List<Appearance> appearances = new ArrayList<>();

        for (AppearanceEntity appearanceEntity : appearanceEntities) {
            Appearance appearance = new Appearance();
            BeanUtils.copyProperties(appearanceEntity, appearance);
            appearances.add(appearance);
        }

        return AppearanceResponse.builder()
                .appearances(appearances)
                .response(AppConstant.SUCCESS)
                .build();
    }

    public CommonResponse appearanceImageUpsert(String imageUrl, String appearanceName, String uniqueInteractionId) throws IOException {

        CommonResponse response = new CommonResponse();

        Optional<AppearanceEntity> appearanceOptional = repository.findByAppearanceName(appearanceName);

        if (appearanceOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + "  appearance is not found .... ");
            return response;
        }
        try {
            AppearanceEntity appearance = appearanceOptional.get();
            appearance.setAppearanceImage(imageUrl);
            appearance.setLifeCycles(utils.upsertLifeCycles("appearance image added", appearance.getLifeCycles()));
            appearance.setAudit(utils.updateAudit(uniqueInteractionId, appearance.getAudit()));
            repository.save(appearance);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + " " + e.getMessage());
        }
        return response;
    }
}
