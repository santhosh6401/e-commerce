package com.ecommerce.clothing.customer.model.entity;

import com.ecommerce.clothing.customer.model.common.Audit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "complaint")
public class ComplaintsEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNo;
    private String message;
    private Audit audit;
}
