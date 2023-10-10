package com.ecommerce.clothing.appearance.model.response.appearance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppearanceResponse {
    private List<Appearance> appearances = new ArrayList<>();
    private String response;
}
