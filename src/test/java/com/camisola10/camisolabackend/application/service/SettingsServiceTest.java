package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.RetrieveSettingsUseCase;
import com.camisola10.camisolabackend.application.port.out.SettingsDB;
import com.camisola10.camisolabackend.domain.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettingsServiceTest {

    @Mock
    private SettingsDB db;
    private RetrieveSettingsUseCase service;

    @BeforeEach
    public void setUp() {
        service = new SettingsService(db);
    }

    @Test
    public void shouldRetrieveAllSettings() {

        var settingsMock = mock(Settings.class);
        when(db.findAll()).thenReturn(settingsMock);

        Settings settings = service.getSettings();

        assertThat(settings).isEqualTo(settingsMock);
        verify(db).findAll();
        verifyNoMoreInteractions(db);
    }
}
