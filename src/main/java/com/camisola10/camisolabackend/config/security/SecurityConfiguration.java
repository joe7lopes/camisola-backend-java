package com.camisola10.camisolabackend.config.security;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.camisola10.camisolabackend.domain.user.User.Role.ADMIN;
import static com.camisola10.camisolabackend.domain.user.User.Role.SHOP_MANAGER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@Profile("!local")
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(GET, ApiUrl.PRODUCTS)
                .permitAll()
                .antMatchers(POST, ApiUrl.PRODUCTS)
                .hasAnyRole(ADMIN.name(), SHOP_MANAGER.name());

    }
}
