package com.ecommerce.clothing.appearance.model.request;

import com.ecommerce.clothing.appearance.model.enums.ProductSize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequest {
    private String productId;
    private String productName;
    private String title;
    private String productDescription;
    private List<String> productTag;
    private List<ProductSize> productSize;
    private String highlights;
    private int offer;
    private BigDecimal orgPrice;
    private boolean orgPriceLined;
    private BigDecimal offerPrice;
    private String image;
    private int rating;
    private int quantity;
    private boolean dashboardFav;
}
