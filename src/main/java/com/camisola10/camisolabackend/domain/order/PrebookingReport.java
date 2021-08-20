package com.camisola10.camisolabackend.domain.order;

import lombok.Data;

@Data
public class PrebookingReport {
    private final String productName;
    private final String size;
    private final int quantity;
}
