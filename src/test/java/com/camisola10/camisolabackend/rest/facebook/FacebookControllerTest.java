package com.camisola10.camisolabackend.rest.facebook;

import com.camisola10.camisolabackend.application.service.facebook.FacebookService;
import com.camisola10.camisolabackend.rest.ApiUrl;
import com.camisola10.camisolabackend.rest.ControllerTest;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ControllerTest(controllers = FacebookController.class)
class FacebookControllerTest {

    @MockBean
    private FacebookService facebookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSaveToken() throws Exception {
        val requestBody = "{ \"userToken\" : \"1233\" }";
        mockMvc.perform(post(ApiUrl.FB)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(facebookService).storeToken("1233");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnPageReviews() throws Exception {
        mockMvc.perform(get(ApiUrl.FB + "/reviews"))
                .andExpect(status().isOk());

        verify(facebookService).getPageReviews();
    }

    @Test
    void shouldReturn403WhenSaveToken() throws Exception {
        val requestBody = "{ \"userToken\" : \"1233\" }";
        mockMvc.perform(post(ApiUrl.FB).content(requestBody))
                .andExpect(status().isForbidden());

        verifyNoInteractions(facebookService);
    }

    @Test
    void shouldReturn403PageReviews() throws Exception {
        mockMvc.perform(get(ApiUrl.FB + "/reviews"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(facebookService);
    }

}