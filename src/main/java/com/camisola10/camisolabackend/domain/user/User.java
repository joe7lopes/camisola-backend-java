package com.camisola10.camisolabackend.domain.user;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;


@Value
@Builder
@RequiredArgsConstructor
public class User {
    @Builder.Default
    String userId = UUID.randomUUID().toString();
    String firstName;
    String lastName;
    Address address;
    Email email;
    String password;

    public static Email withEmail(String email) {
        return new Email(email);
    }

    public String getEmail() {
        return email.asString();
    }

}
