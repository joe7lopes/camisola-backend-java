package com.camisola10.camisolabackend.application.port.in.command.product;

import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.apache.http.entity.ContentType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateProductCommand {
    private String name;
    private List<Base64Image> images;
    private List<ProductSize> sizes;
    private List<ProductCategory> categories;
    private Money defaultPrice;
    private boolean customizable;

    @Value
    public static class Base64Image {
        String name;
        byte[] file;
        ContentType contentType;
        boolean isDefault;
    }
}
