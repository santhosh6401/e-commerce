package com.ecommerce.clothing.appearance.model.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private String categoryName;
    private String order;
    private boolean newLabel;
}
