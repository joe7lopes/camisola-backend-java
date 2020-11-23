package com.camisola10.camisolabackend.rest.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
class SignInRequest {
    private String email;
    private String password;
}
