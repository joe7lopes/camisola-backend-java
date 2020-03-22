package com.camisola10.camisolabackend.application.port.in.command.product;

import com.camisola10.camisolabackend.application.port.in.command.SelfValidating;
import com.camisola10.camisolabackend.domain.ProductCategory;
import com.camisola10.camisolabackend.domain.ProductImage;
import com.camisola10.camisolabackend.domain.ProductSize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class CreateProductCommand extends SelfValidating<CreateProductCommand> {
    @NotBlank
    String name;
    Set<ProductImage> images;
    @NotEmpty
    Set<ProductSize> sizes;
    @NotEmpty
    Set<ProductCategory> categories;
    boolean isCustomizable;
}
