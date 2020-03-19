package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.adapter.presistence.UserRepository;
import com.camisola10.camisolabackend.application.port.in.SignUpUseCase;
import com.camisola10.camisolabackend.application.port.in.command.RegisterUserCommand;
import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService implements SignUpUseCase {

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignUpService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signUp(RegisterUserCommand command) {
        var email = new Email(command.getEmail());
        var password = bCryptPasswordEncoder.encode(command.getPassword());
        User user = User.builder()
                .userId("some")
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(email)
                .password(password)
                .build();

        repository.save(user);
    }
}
