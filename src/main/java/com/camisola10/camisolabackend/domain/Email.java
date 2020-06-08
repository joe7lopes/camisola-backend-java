package com.camisola10.camisolabackend.domain;

import lombok.Value;

import static java.util.Objects.isNull;

@Value
public class Email {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    String value;

    public Email(String value) {
        this.value = value;
        validate();
    }

    public static Email from(String value){
        return new Email(value);
    }

    public String asString(){
        return value;
    }

    private void validate() {
        if(isNull(value) || !value.matches(EMAIL_REGEX)){
            throw new InvalidEmailException(String.format("Invalid email address %s", value));
        }
    }

    public static class InvalidEmailException extends RuntimeException{
        public InvalidEmailException(String message) {
            super(message);
        }
    }
}
