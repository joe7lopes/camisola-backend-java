package com.camisola10.camisolabackend.persistence.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Document("user")
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
@Data
class UserDb {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles;
}
