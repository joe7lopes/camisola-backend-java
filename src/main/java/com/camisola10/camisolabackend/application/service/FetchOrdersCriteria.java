package com.camisola10.camisolabackend.application.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FetchOrdersCriteria {
    int page;
    int pageSize;
    String sortBy;
    String orderId;
    String name;
    String phone;
    String createdAt;
}
