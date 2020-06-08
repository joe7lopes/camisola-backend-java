package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.SignInUseCase;
import com.camisola10.camisolabackend.config.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService implements SignInUseCase {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;

    @Override
    public String signIn(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(authentication);
    }
}
