package com.camisola10.camisolabackend.persistence.product;


import com.camisola10.camisolabackend.domain.images.Image;
import com.camisola10.camisolabackend.domain.product.Badge;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @With
    private List<Badge> badges;
    private BigDecimal defaultPrice;
    private String description;
    private boolean customizable;
    private boolean visible;
    private boolean prebooking;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModified;
}
