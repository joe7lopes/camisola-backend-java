package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.SignUpUseCase;
import com.camisola10.camisolabackend.application.port.in.command.user.RegisterUserCommand;
import com.camisola10.camisolabackend.application.port.out.UserDB;
import com.camisola10.camisolabackend.domain.user.User;
import com.camisola10.camisolabackend.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.camisola10.camisolabackend.domain.user.Role.USER;

@Service
@RequiredArgsConstructor
class SignUpService implements SignUpUseCase {

    private final UserDB userDB;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(RegisterUserCommand command) {
        var password = passwordEncoder.encode(command.getPassword());
        var user = User.builder()
                .id(UserId.create())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .password(password)
                .roles(Set.of(USER))
                .build();

        this.userDB.save(user);
    }
}
