package com.camisola10.camisolabackend.domain.User;

import lombok.Value;

@Value
class Email {
    private String email;

    public String asString(){
        return email;
    }
}
