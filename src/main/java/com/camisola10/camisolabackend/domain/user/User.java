package com.camisola10.camisolabackend.domain.user;

import lombok.Builder;
import lombok.Value;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;


@Value
@Builder
public class User {
    String id;
    String firstName;
    String lastName;
    Address address;
    Email email;
    Password password;
    String[] roles;

    private User(String id, String firstName, String lastName, Address address, Email email, Password password, String[] roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.roles = roles;
        validate();
    }

    private void validate() {
        boolean invalid =
                isBlank(id) ||
                isBlank(firstName) ||
                isBlank(lastName) ||
                isNull(address) ||
                isNull(email) ||
                isNull(password) ||
                roles.length <= 0;

        if (invalid) throw new InvalidUserException(this);
    }

    static class InvalidUserException extends RuntimeException {
        public InvalidUserException(User user) {
            super("Invalid user" + user);
        }
    }
}
