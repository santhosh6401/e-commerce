package com.ecommerce.clothing.customer.model.entity;

import com.ecommerce.clothing.customer.model.common.Audit;
import com.ecommerce.clothing.customer.model.common.LifeCycle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "feedback")
public class FeedbackEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNo;
    private String message;
    private Audit audit;
}
