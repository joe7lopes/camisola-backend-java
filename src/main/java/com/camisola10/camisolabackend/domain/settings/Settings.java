package com.camisola10.camisolabackend.domain.settings;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Settings {

    private List<String> productSizes;
    private List<String> productCategories;
}
