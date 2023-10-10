package com.ecommerce.clothing.customer.model.response.faqs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQsResponse {
    private List<FAQs> faQs;
    private String response;
}
