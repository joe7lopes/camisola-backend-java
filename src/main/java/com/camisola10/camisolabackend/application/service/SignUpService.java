package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.SignUpUseCase;
import com.camisola10.camisolabackend.application.port.in.command.user.RegisterUserCommand;
import com.camisola10.camisolabackend.application.port.out.UserDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
class SignUpService implements SignUpUseCase {

    private final UserDB userDB;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void signUp(RegisterUserCommand command) {
//        var email = User.withEmail(command.getEmail());
////        var password = bCryptPasswordEncoder.encode(command.getPassword());
//var password = "as";
//        User user = User.builder()
//                .firstName(command.getFirstName())
//                .lastName(command.getLastName())
//                .email(email)
//                .password(password)
//                .build();

     //   this.userDB.save(user);
    }
}
