package com.camisola10.camisolabackend.adapter.rest.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
