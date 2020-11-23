package com.camisola10.camisolabackend.domain.user;

import com.camisola10.camisolabackend.domain.EmailAddress;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
@Builder
public class User {
    UserId id;
    String firstName;
    String lastName;
    EmailAddress emailAddress;
    String password;
    Set<Role> roles;

    private User(UserId id, String firstName, String lastName, EmailAddress emailAddress, String password, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.roles = roles;
        validate();
    }

    private void validate() {
        boolean invalid =
                isNull(id) ||
                        isBlank(firstName) ||
                        isBlank(lastName) ||
                        isNull(emailAddress) ||
                        isNull(password) ||
                        isNull(roles) ||
                        roles.size() <= 0;

        if (invalid) throw new InvalidUserException(this);
    }

    static class InvalidUserException extends RuntimeException {
        public InvalidUserException(User user) {
            super("Invalid user: " + user);
        }
    }

}
