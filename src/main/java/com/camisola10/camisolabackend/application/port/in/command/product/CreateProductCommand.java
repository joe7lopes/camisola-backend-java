package com.camisola10.camisolabackend.application.port.in.command.product;

import com.camisola10.camisolabackend.application.port.in.command.SelfValidating;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductCommand {
    private String name;
    private List<ProductImage> images;
    private List<ProductSize> sizes;
    private List<ProductCategory> categories;
    private Money defaultPrice;
    private boolean isCustomizable;

//    public CreateProductCommand(String name,
//                                List<ProductImage> images,
//                                List<ProductSize> sizes,
//                                List<ProductCategory> categories,
//                                boolean isCustomizable,
//                                Money defaultPrice) {
//        this.name = name;
//        this.images = images;
//        this.sizes = sizes;
//        this.categories = categories;
//        this.isCustomizable = isCustomizable;
//        this.defaultPrice = defaultPrice;
//        validateSelf();
//    }
}
