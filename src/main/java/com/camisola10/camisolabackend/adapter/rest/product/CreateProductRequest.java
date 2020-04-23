package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.domain.product.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class CreateProductRequest {
    public String name;
    public List<ProductImage> images;
    public List<ProductSizeDto> sizes;
    public List<ProductCategoryDto> categories;
    public boolean isCustomizable;
    public String defaultPrice;
}
