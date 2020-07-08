package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.domain.product.ProductImage;
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
class ProductResponseDto {
    private String id;
    private String name;
    private List<String> categories;
    private List<ProductSizeResponseDto> sizes;
    private boolean customizable;
    private List<ProductImage> images;
    private String defaultPrice;

}
