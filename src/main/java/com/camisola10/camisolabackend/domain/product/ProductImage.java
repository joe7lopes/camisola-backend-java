package com.camisola10.camisolabackend.domain.product;

import lombok.Value;

@Value
public class ProductImage {
    private static final String DEFAULT_IMAGE_URL = "https://camisola-backend.s3-eu-west-1.amazonaws.com/defaultshirt.png";
    private static final String DEFAULT_IMAGE_NAME = "camisola10";
    String name;
    String url;
    boolean isDefault;

    static ProductImage createDefault(){
        return new ProductImage(DEFAULT_IMAGE_NAME, DEFAULT_IMAGE_URL,true);
    }
}
