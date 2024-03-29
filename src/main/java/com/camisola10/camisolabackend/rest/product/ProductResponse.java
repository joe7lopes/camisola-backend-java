package com.camisola10.camisolabackend.rest.product;

import com.camisola10.camisolabackend.domain.product.Badge;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;

@NoArgsConstructor
@AllArgsConstructor(access = PACKAGE)
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class ProductResponse {
    private String id;
    private String name;
    private List<String> categories;
    private List<ProductSizeResponseDto> sizes;
    private List<Badge> badges;
    private List<ProductImageResponse> images;
    private String defaultPrice;
    private String description;
    private boolean customizable;
    private boolean visible;
    private boolean prebooking;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class ProductImageResponse {
        String id;
        String name;
        String url;
    }
}
