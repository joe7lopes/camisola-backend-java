package com.camisola10.camisolabackend.persistence.settings;

import com.camisola10.camisolabackend.application.port.out.SettingsDB;
import com.camisola10.camisolabackend.domain.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettingsAdapterTest {

    @Mock
    private SettingsRepository repository;
    private SettingsDB settingsDB;

    @BeforeEach
    public void setUp() {
        settingsDB = new SettingsAdapter(repository);
    }

    @Test
    public void shouldRetrieveSettings() {
        var settingsMock = mock(Settings.class);
        when(repository.findAll()).thenReturn(List.of(settingsMock));

        Settings settings = settingsDB.findAll();

        assertThat(settings).isEqualTo(settingsMock);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

}
