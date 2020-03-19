package com.camisola10.camisolabackend.domain;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private Address address;
    private Email email;
    private String password;
}
