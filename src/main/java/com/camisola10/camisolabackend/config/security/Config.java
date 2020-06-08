package com.camisola10.camisolabackend.config.security;

import com.camisola10.camisolabackend.config.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Config {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthenticationFilter jwtFilter(UserDetailsService userDetailsService, TokenProvider tokenProvider) {
        return new JwtAuthenticationFilter(userDetailsService, tokenProvider);
    }

    @Bean
    TokenProvider tokenProvider(JwtProperties properties) {
        return new TokenProvider(properties);
    }

}
