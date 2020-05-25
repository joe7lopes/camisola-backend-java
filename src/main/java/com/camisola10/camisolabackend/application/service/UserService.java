package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.out.UserDB;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
class UserService implements UserDetailsService {

    private final UserDB db;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = db.findByUserName()
                .orElseThrow(() -> new UsernameNotFoundException(format("username %s not found", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getFirstName(),
                user.getPassword().asString(),
                AuthorityUtils.createAuthorityList(user.getRoles()));
    }
}
