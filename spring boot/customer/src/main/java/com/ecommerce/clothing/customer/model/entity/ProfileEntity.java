package com.ecommerce.clothing.customer.model.entity;

import com.ecommerce.clothing.customer.model.common.Audit;
import com.ecommerce.clothing.customer.model.common.LifeCycle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "profile")
public class ProfileEntity {
    @Id
    private String profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String password;
    private String passwordResetKey;
    private List<LifeCycle> lifeCycles = new ArrayList<>();
    private Audit audit;
}
