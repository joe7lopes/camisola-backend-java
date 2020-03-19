package com.camisola10.camisolabackend.adapter.rest;

import com.camisola10.camisolabackend.adapter.rest.dto.SignUpDto;
import com.camisola10.camisolabackend.application.port.in.SignUpUseCase;
import com.camisola10.camisolabackend.application.port.in.command.RegisterUserCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpUseCase signUpUseCase;

    @Test
    public void signUpWithoutEmailReturns400() throws Exception {
        String firstName = "";
        String lastName = "rodrigues";
        String password = "123";
        var requestBody = new SignUpDto(firstName, lastName, null, password);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                post(ApiUrl.SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());

        RegisterUserCommand command = new RegisterUserCommand(firstName, lastName, null, password);
        verify(signUpUseCase).signUp(command);
    }

    @Test
    public void shouldSignUpUser() throws Exception {
        String firstName = "antonio";
        String lastName = "rodrigues";
        String email = "adasd@asd.com";
        String password = "123";
        var requestBody = new SignUpDto(firstName, lastName, email, password);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                post(ApiUrl.SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());

        RegisterUserCommand command = new RegisterUserCommand(firstName, lastName, email, password);
        verify(signUpUseCase).signUp(command);
    }
}
