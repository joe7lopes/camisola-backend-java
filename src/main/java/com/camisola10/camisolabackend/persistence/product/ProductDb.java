package com.camisola10.camisolabackend.persistence.product;


import com.camisola10.camisolabackend.domain.images.Image;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
class ProductDb {
    @Id
    private String productId;
    private String name;
    private List<ProductCategory> categories;
    private List<ProductSize> sizes;
    private List<Image> images;
    private boolean customizable;
    //TODO: DB migration tool needed.
    private boolean visible = true;
    private BigDecimal defaultPrice;
    private String description;
}
