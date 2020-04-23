package com.camisola10.camisolabackend.application.port.in.command.product;

import com.camisola10.camisolabackend.application.port.in.command.SelfValidating;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class CreateProductCommand extends SelfValidating<CreateProductCommand> {
    @NotBlank
    String name;
    List<ProductImage> images;
    @NotEmpty
    List<ProductSize> sizes;
    @NotEmpty
    List<ProductCategory> categories;
    boolean isCustomizable;
    @NotNull
    Money defaultPrice;

    public CreateProductCommand(String name,
                                List<ProductImage> images,
                                List<ProductSize> sizes,
                                List<ProductCategory> categories,
                                boolean isCustomizable,
                                Money defaultPrice) {
        this.name = name;
        this.images = images;
        this.sizes = sizes;
        this.categories = categories;
        this.isCustomizable = isCustomizable;
        this.defaultPrice = defaultPrice;
        validateSelf();
    }
}
