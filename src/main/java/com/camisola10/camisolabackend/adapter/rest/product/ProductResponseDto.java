package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import com.camisola10.camisolabackend.domain.product.ProductSize;
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
    private List<ProductCategory> categories;
    private List<ProductSize> sizes;
    private boolean customizable;
    private List<ProductImage> images;
    private String defaultPrice;

}
