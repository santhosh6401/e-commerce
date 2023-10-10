package com.ecommerce.clothing.appearance.model.response.category;

import lombok.Data;

@Data
public class Category {
    private String categoryName;
    private String categoryImage;
    private String order;
    private boolean newLabel;
}
