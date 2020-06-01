package com.camisola10.camisolabackend.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static com.camisola10.camisolabackend.adapter.rest.ApiUrl.ORDERS;
import static com.camisola10.camisolabackend.adapter.rest.ApiUrl.PRODUCTS;
import static com.camisola10.camisolabackend.domain.user.User.Role.ADMIN;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Profile("local")
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfigurationLocal extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Run LOCAL Security Configuration");
        http.authorizeRequests()
                .antMatchers(POST, ORDERS).permitAll()
                .antMatchers(GET, PRODUCTS).permitAll()
                .antMatchers(POST, PRODUCTS).hasRole(ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("sergey")
                .password("{noop}password")
                .roles(ADMIN.name());
    }
}