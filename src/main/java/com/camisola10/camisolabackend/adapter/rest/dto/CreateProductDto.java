package com.camisola10.camisolabackend.adapter.rest.dto;

import com.camisola10.camisolabackend.domain.ProductCategory;
import com.camisola10.camisolabackend.domain.ProductImage;
import com.camisola10.camisolabackend.domain.ProductSize;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Value
public class CreateProductDto {
    String name;
    Set<ProductImage> images;
    Set<ProductSize> sizes;
    Set<ProductCategory> categories;
    boolean isCustomizable;
}
