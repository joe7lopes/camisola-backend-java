package com.camisola10.camisolabackend.domain;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
public class ProductCategory {
    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
