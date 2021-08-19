package com.camisola10.camisolabackend.rest.product;

import com.camisola10.camisolabackend.domain.product.Badge;
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
    public List<Badge> badges;
    public String defaultPrice;
    public String description;
    public boolean customizable;
    public boolean visible;
    public boolean prebooking;
}
