package com.camisola10.camisolabackend.adapter.persistence.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = "password")
class UserDb {
    @Id
    protected Long userId;
    private String password;
    private String[] roles;
}
