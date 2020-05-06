package com.camisola10.camisolabackend.adapter.rest.settings;

import com.camisola10.camisolabackend.application.port.in.RetrieveSettingsUseCase;
import com.camisola10.camisolabackend.domain.settings.Settings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SettingsController.class)
class SettingsControllerTest {

    @MockBean
    private RetrieveSettingsUseCase retrieveSettingsUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnSettings() throws Exception {

        var settingsMock = mock(Settings.class);
        when(retrieveSettingsUseCase.getSettings()).thenReturn(settingsMock);

        mockMvc.perform(get("/api/settings"))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(retrieveSettingsUseCase).getSettings();
        verifyNoMoreInteractions(retrieveSettingsUseCase);
    }

}
