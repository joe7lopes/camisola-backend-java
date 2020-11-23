package com.camisola10.camisolabackend.domain;

import lombok.Value;

import static java.util.Objects.isNull;

@Value
public class EmailAddress {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    String value;

    public EmailAddress(String value) {
        this.value = value;
        validate();
    }

    public static EmailAddress from(String value){
        return new EmailAddress(value);
    }

    public String asString(){
        return value;
    }

    private void validate() {
        if(isNull(value) || !value.matches(EMAIL_REGEX)){
            throw new InvalidEmailAddressException(String.format("Invalid email address %s", value));
        }
    }

    public static class InvalidEmailAddressException extends RuntimeException{
        public InvalidEmailAddressException(String message) {
            super(message);
        }
    }
}
