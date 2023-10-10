package com.ecommerce.clothing.appearance.model.entity;


import com.ecommerce.clothing.appearance.model.common.Audit;
import com.ecommerce.clothing.appearance.model.common.LifeCycle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data

@Document(collection = "category")
public class CategoryEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String categoryName;
    private String categoryImage;
    private String order;
    private boolean newLabel;
    private List<LifeCycle> lifeCycles = new ArrayList<>();
    private Audit audit;
}
