package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.domain.product.ProductImage;
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
class ProductResponseDto {
    private String id;
    private String name;
    private List<ProductCategoryDto> categories;
    private List<ProductSizeDto> sizes;
    private boolean customizable;
    private List<ProductImage> images;
    private String defaultPrice;

}
