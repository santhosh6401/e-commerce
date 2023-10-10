package com.ecommerce.clothing.appearance.controller;

import com.ecommerce.clothing.appearance.model.request.CategoryRequest;
import com.ecommerce.clothing.appearance.model.response.CommonResponse;
import com.ecommerce.clothing.appearance.model.response.category.CategoryResponse;
import com.ecommerce.clothing.appearance.service.CategoryService;
import com.ecommerce.clothing.appearance.utils.UserValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Category", value = "Category")
@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final UserValidation userValidation;

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(@RequestHeader String uniqueInteractionId,
                                           @RequestBody CategoryRequest request,
                                           @RequestHeader(name = "user-name") String userName,
                                           @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} create the content config", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return categoryService.createCategory(request, uniqueInteractionId);
    }

    @PutMapping
    public CategoryResponse updateCategory(@RequestHeader String uniqueInteractionId,
                                           @RequestBody CategoryRequest request,
                                           @RequestHeader(name = "user-name") String userName,
                                           @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} update the category request", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return categoryService.updateCategory(request, uniqueInteractionId);
    }

    @DeleteMapping
    public CommonResponse deleteCategory(@RequestHeader String uniqueInteractionId,
                                         @RequestParam String name,
                                         @RequestHeader(name = "user-name") String userName,
                                         @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] name : {} delete the category based on id", uniqueInteractionId, name);
        userValidation.validate(userName, password);
        return categoryService.deleteCategory(name);
    }

    @GetMapping
    public CategoryResponse getCategory(
            @RequestHeader String uniqueInteractionId,
            @RequestParam(required = false, value = "category-name", name = "category-name") @ApiParam(name = "category-name") String categoryName,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] category-name : {} get category", uniqueInteractionId, categoryName);
        userValidation.validate(userName, password);
        return categoryService.getCategory(categoryName);
    }

    @PostMapping("/upload-img")
    public CommonResponse categoryImgUploadUpsert(@RequestHeader String uniqueInteractionId,
                                                  @RequestParam String imageUrl,
                                                  @RequestParam String categoryName,
                                                  @RequestHeader(name = "user-name") String userName,
                                                  @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] and category : {} upsert image the category", uniqueInteractionId, categoryName);
        userValidation.validate(userName, password);
        return categoryService.categoryImageUpsert(imageUrl, categoryName, uniqueInteractionId);
    }
}
