package com.camisola10.camisolabackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private long expirationTime;
}
