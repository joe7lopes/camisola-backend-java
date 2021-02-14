package com.camisola10.camisolabackend.domain.facebook;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Optional;

@Document
@Data(staticConstructor = "of")
public class FacebookSettings {

    @Id
    private static Long id = 1L;
    private final String longLivedPageAccessToken;
    private final LocalDateTime longLivedPageAccessTokenIssuedAt;

    public Optional<String> getLongLivedPageAccessToken() {
        return Optional.ofNullable(longLivedPageAccessToken);
    }

    public Optional<LocalDateTime> getLongLivedPageAccessTokenIssuedAt() {
        return Optional.ofNullable(longLivedPageAccessTokenIssuedAt);
    }
}
