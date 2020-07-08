package com.camisola10.camisolabackend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

import static com.camisola10.camisolabackend.adapter.rest.ApiUrl.ORDERS;
import static com.camisola10.camisolabackend.adapter.rest.ApiUrl.PRODUCTS;
import static com.camisola10.camisolabackend.adapter.rest.ApiUrl.SIGN_IN;
import static com.camisola10.camisolabackend.adapter.rest.ApiUrl.SIGN_UP;
import static com.camisola10.camisolabackend.adapter.rest.ApiUrl.USERS;
import static com.camisola10.camisolabackend.domain.user.Role.ADMIN;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;
    private final JwtAuthenticationFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .mvcMatchers(GET, ORDERS).hasRole(ADMIN.name())
                .mvcMatchers(POST, ORDERS+"/{#orderId}").hasRole(ADMIN.name())
                .mvcMatchers(GET, ORDERS).hasRole(ADMIN.name())
                .mvcMatchers(POST, ORDERS).permitAll()
                .mvcMatchers(POST, PRODUCTS).hasRole(ADMIN.name())
                .mvcMatchers(PUT, PRODUCTS+ "/{#productId}").hasRole(ADMIN.name())
                .mvcMatchers(DELETE, PRODUCTS + "/{#productId}").hasRole(ADMIN.name())
                .mvcMatchers(GET, PRODUCTS).permitAll()
                .mvcMatchers(USERS + SIGN_IN, USERS + SIGN_UP).permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
