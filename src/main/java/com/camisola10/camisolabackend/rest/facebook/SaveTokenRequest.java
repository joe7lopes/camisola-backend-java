package com.camisola10.camisolabackend.rest.facebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class SaveTokenRequest {
    private String userToken;
}
