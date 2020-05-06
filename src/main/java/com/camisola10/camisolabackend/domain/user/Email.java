package com.camisola10.camisolabackend.domain.user;

import lombok.Value;

@Value
class Email {
    private String email;

    public String asString(){
        return email;
    }
}
