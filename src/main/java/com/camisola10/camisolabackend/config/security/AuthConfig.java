package com.camisola10.camisolabackend.config.security;

import com.camisola10.camisolabackend.adapter.presistence.UserRepository;
import com.camisola10.camisolabackend.application.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class AuthConfig {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    @Profile("!test")
    UserDetailsService UserDetailsService(UserRepository repository){
        return new UserDetailsServiceImpl(repository);
    }

    @Bean
    @Profile("test")
    UserDetailsService UserDetailsServiceMock(UserRepository repository){
        return null;
    }
}
