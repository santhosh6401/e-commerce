package com.ecommerce.clothing.appearance.service;

import com.ecommerce.clothing.appearance.constant.AppConstant;
import com.ecommerce.clothing.appearance.model.entity.CategoryEntity;
import com.ecommerce.clothing.appearance.model.request.CategoryRequest;
import com.ecommerce.clothing.appearance.model.response.CommonResponse;
import com.ecommerce.clothing.appearance.model.response.category.Category;
import com.ecommerce.clothing.appearance.model.response.category.CategoryResponse;
import com.ecommerce.clothing.appearance.repository.CategoryRepository;
import com.ecommerce.clothing.appearance.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    private final HelperUtils utils;

    private final MongoTemplate mongoTemplate;

    public CategoryResponse createCategory(CategoryRequest request, String uniqueInteractionId) {

        CategoryResponse response = new CategoryResponse();

        Optional<CategoryEntity> categoryOptional = repository.findByCategoryName(request.getCategoryName());

        if (categoryOptional.isPresent()) {
            response.setCategories(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + "  category is already exists .... ");
            return response;
        }
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(utils.generateId("CR"));
            BeanUtils.copyProperties(request, categoryEntity);
            categoryEntity.setAudit(utils.createAudit(uniqueInteractionId));
            categoryEntity.setLifeCycles(utils.upsertLifeCycles("category created ", categoryEntity.getLifeCycles()));
            repository.save(categoryEntity);
            Category category = new Category();
            BeanUtils.copyProperties(categoryEntity, category);
            response.setCategories(Collections.singletonList(category));
            response.setResponse(AppConstant.SUCCESS);

        } catch (Exception e) {
            response.setCategories(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;
    }

    public CategoryResponse updateCategory(CategoryRequest request, String uniqueInteractionId) {
        CategoryResponse response = new CategoryResponse();
        Optional<CategoryEntity> categoryOptional = repository.findByCategoryName(request.getCategoryName());

        if (categoryOptional.isEmpty()) {
            response.setCategories(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + "  category is not found .... ");
            return response;
        }

        try {
            CategoryEntity categoryEntity = categoryOptional.get();
            BeanUtils.copyProperties(request, categoryEntity);
            categoryEntity.setAudit(utils.updateAudit(uniqueInteractionId, categoryEntity.getAudit()));
            categoryEntity.setLifeCycles(utils.upsertLifeCycles("category updated", categoryEntity.getLifeCycles()));
            repository.save(categoryEntity);
            Category category = new Category();
            BeanUtils.copyProperties(categoryEntity, category);
            response.setCategories(Collections.singletonList(category));
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setCategories(new ArrayList<>());
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }
        return response;
    }

    public CommonResponse deleteCategory(String name) {
        CommonResponse response = new CommonResponse();

        if (Objects.isNull(name)) {
            response.setResponse(AppConstant.FAILED + "  category is missing .... ");
            return response;
        }

        Optional<CategoryEntity> categoryOptional = repository.findByCategoryName(name);
        if (categoryOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + "  category not found .... ");
            return response;
        }

        try {
            repository.delete(categoryOptional.get());
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }

        return response;
    }

    public CategoryResponse getCategory(String categoryName) {

        Query query = new Query();
        if (Objects.nonNull(categoryName)) {
            query.addCriteria(Criteria.where("name").is(categoryName));
        }

        List<CategoryEntity> categories = mongoTemplate.find(query, CategoryEntity.class);

        if (categories.isEmpty()) {
            return CategoryResponse.builder()
                    .categories(new ArrayList<>())
                    .response(AppConstant.FAILED + " no record found")
                    .build();
        }

        List<Category> categoryList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categories) {
            Category category = new Category();
            BeanUtils.copyProperties(categoryEntity, category);
            categoryList.add(category);
        }
        return CategoryResponse.builder()
                .categories(categoryList)
                .response(AppConstant.SUCCESS)
                .build();

    }

    public CommonResponse categoryImageUpsert(String imageUrl, String name, String uniqueInteractionId) throws Exception {

        CommonResponse response = new CommonResponse();

        Optional<CategoryEntity> categoryOptional = repository.findByCategoryName(name);

        if (categoryOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + "  category is not found .... ");
            return response;
        }
        try {
            CategoryEntity categoryEntity = categoryOptional.get();
            categoryEntity.setCategoryImage(imageUrl);
            categoryEntity.setLifeCycles(utils.upsertLifeCycles("category image added", categoryEntity.getLifeCycles()));
            categoryEntity.setAudit(utils.updateAudit(uniqueInteractionId, categoryEntity.getAudit()));
            repository.save(categoryEntity);
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }
        return response;
    }
}
