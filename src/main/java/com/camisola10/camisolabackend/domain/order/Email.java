package com.camisola10.camisolabackend.domain.order;

import lombok.Value;

@Value
class Email {
    String value;

    public Email(String value) {
        this.value = value;
        validate();
    }

    private void validate() {
        //TODO
    }
}
