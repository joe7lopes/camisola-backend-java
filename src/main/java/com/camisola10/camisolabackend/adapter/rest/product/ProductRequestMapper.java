package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
class ProductRequestMapper {

    CreateProductCommand toCommand(CreateProductRequest request) {
        return new CreateProductCommand(
                request.name,
                request.images,
                toProductSize(request.sizes),
                toProductCategory(request.categories),
                request.isCustomizable,
                Money.from(request.defaultPrice)
        );
    }

    private List<ProductSize> toProductSize(List<ProductSizeDto> sizes) {
        return sizes.stream().map(s -> new ProductSize(new Size(s.size), new Money(new BigDecimal(s.price))))
                .collect(Collectors.toList());
    }

    private List<ProductCategory> toProductCategory(List<ProductCategoryDto> categories) {
        return categories.stream().map(c -> new ProductCategory(c.name, c.displayName))
                .collect(Collectors.toList());
    }
}
