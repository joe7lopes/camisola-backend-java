package com.camisola10.camisolabackend.adapter.rest.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
class SignInResponse {
    private String token;
}
