package com.ecommerce.clothing.customer.model.entity;

import com.ecommerce.clothing.customer.model.common.Audit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "faq")
public class FAQsEntity {
    @Id
    private String id;
    private String query;
    private String answer;
    private Audit audit;
}
