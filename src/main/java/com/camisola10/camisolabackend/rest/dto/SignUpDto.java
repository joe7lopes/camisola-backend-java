package com.camisola10.camisolabackend.rest.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SignUpDto {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public SignUpDto() {
    }
}
