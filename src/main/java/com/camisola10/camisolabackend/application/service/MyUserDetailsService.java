package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.out.UserDB;
import com.camisola10.camisolabackend.domain.EmailAddress;
import com.camisola10.camisolabackend.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserDB userDB;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var email = EmailAddress.from(username);
        var applicationUser = userDB.findByEmail(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(username);
                });

        return new User(email.asString(), applicationUser.getPassword(), getAuthorities(applicationUser.getRoles()));
    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.name()))
                .collect(Collectors.toList());
    }

}
