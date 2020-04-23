package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ProductResponseDto {
    public String id;
    public String name;
    public List<ProductCategory> categories;
    public List<ProductSize> sizes;
    public boolean isCustomizable;
    public List<ProductImage> images;

    public ProductResponseDto(Product product) {
        this.id = product.getId().getValue();
        this.name = product.getName();
        this.categories = product.getCategories();
        this.images = product.getImages();
        this.isCustomizable = product.isCustomizable();
        this.sizes = product.getSizes();
    }
}
