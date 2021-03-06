package com.camisola10.camisolabackend.rest.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
class UpdateProductRequest {
    public String id;
    public String name;
    public List<String> imageIds;
    public List<ProductSizeDto> sizes;
    public List<String> categories;
    public String defaultPrice;
    public boolean isCustomizable;
    public boolean isVisible;
    public String description;
}
