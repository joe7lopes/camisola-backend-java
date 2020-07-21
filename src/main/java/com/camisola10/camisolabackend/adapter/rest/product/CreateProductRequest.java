package com.camisola10.camisolabackend.adapter.rest.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class CreateProductRequest {
    public String name;
    public List<String> images;
    public List<ProductSizeDto> sizes;
    public List<String> categories;
    public String defaultPrice;
    public boolean isCustomizable;
}
