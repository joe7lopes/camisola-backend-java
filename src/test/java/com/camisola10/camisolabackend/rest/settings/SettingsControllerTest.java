package com.camisola10.camisolabackend.rest.settings;

import com.camisola10.camisolabackend.domain.settings.Settings;
import com.camisola10.camisolabackend.persistence.settings.SettingsRepository;
import com.camisola10.camisolabackend.rest.ControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = SettingsController.class)
class SettingsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SettingsRepository repository;

    @Test
    public void shouldReturnSettings() throws Exception {
        var settingsMock = mock(Settings.class);
        var response = List.of(settingsMock);
        Mockito.when(repository.findAll()).thenReturn(response);

        mockMvc.perform(get("/api/settings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(repository).findAll();
    }

    @Test
    public void shouldReturn403ForAnonymousUser() throws Exception {
        mockMvc.perform(post("/api/settings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verifyNoInteractions(repository);
    }

    @Test
    @WithMockUser
    public void shouldReturn403ForNonAdminUser() throws Exception {
        var settings = mock(Settings.class);
        var request = convertToJsonString(settings);

        mockMvc.perform(post("/api/settings")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verifyNoInteractions(repository);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldReturn200ForAdminUser() throws Exception {
        var settings = mock(Settings.class);
        var request = convertToJsonString(settings);

        mockMvc.perform(post("/api/settings")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(repository).save(any(Settings.class));
    }


    private String convertToJsonString(Settings request) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(request);
    }

}