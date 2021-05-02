package com.camisola10.camisolabackend.application.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FetchOrdersCriteria {
    Integer page;
    Integer pageSize;
    String name;
    String phone;

    public boolean hasName() {
        return !isBlank(name);
    }

    public boolean hasPhone() {
        return !isBlank(phone);
    }
}
