package com.camisola10.camisolabackend.domain.user;

import lombok.ToString;
import lombok.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
@ToString(exclude = "value")
public class Password {
    static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    String value;

    private Password(String password) {
        this.value = PASSWORD_ENCODER.encode(password);
        validate();
    }

    public String asString(){
        return value;
    }

    private void validate() {
        if (isBlank(value)) throw new InvalidPasswordException();
    }

    static class InvalidPasswordException extends RuntimeException {
    }
}
