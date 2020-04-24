package com.camisola10.camisolabackend.adapter.persistence.product;


import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("product")
@NoArgsConstructor
@Data
class ProductDb {
    private String productId;
    private String name;
    private List<ProductCategory> categories;
    private List<ProductSize> sizes;
    private List<ProductImage> images;
    private boolean isCustomizable;
    private BigDecimal defaultPrice;
}
