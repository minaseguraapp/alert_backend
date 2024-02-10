package co.minasegura.alert.service;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.GetAlertConfigurationResponse;
import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.mapper.impl.AlertConfigurationMapper;
import co.minasegura.alert.model.AlertConfiguration;
import co.minasegura.alert.repository.IAlertConfigurationRepository;
import co.minasegura.alert.service.impl.AlertConfigurationService;
import co.minasegura.alert.util.CommonsUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlertConfigurationServiceTest {

    private AlertConfigurationService alertConfigurationService;
    private IAlertConfigurationRepository repository;
    private AlertConfigurationMapper mapper;
    private CommonsUtil commonsUtil;

    @Before
    public void setUp() {
        repository = mock(IAlertConfigurationRepository.class);
        mapper = mock(AlertConfigurationMapper.class);
        commonsUtil = mock(CommonsUtil.class);
        alertConfigurationService = new AlertConfigurationService(repository, mapper, commonsUtil);
    }

    @Test
    public void testGetAlertConfiguration() {
        // Arrange
        EnumMap<AlertConfigurationFilter, String> criteria = new EnumMap<>(AlertConfigurationFilter.class);
        criteria.put(AlertConfigurationFilter.MINE, "123456");
        criteria.put(AlertConfigurationFilter.MEASUREMENT_TYPE, "METHANE");

        List<AlertConfigurationEntity> entities = List.of(
                new AlertConfigurationEntity("123456", 123456789L, null, "METHANE")
        );
        when(repository.getAlertConfigurationEntities("123456", "METHANE")).thenReturn(entities);

        // Act
        GetAlertConfigurationResponse response = alertConfigurationService.getAlertConfiguration(criteria);

        // Assert
        assertEquals(entities.size(), response.alertConfigurations().size());
    }

    @Test
    public void testConfigureAlert() {
        // Arrange
        AlertConfiguration alertToRegister = new AlertConfiguration(
                123456789L, "123456", null, null
        );
        AlertConfigurationEntity entity = new AlertConfigurationEntity("123456", 123456789L, null, "METHANE");
        when(mapper.modelToEntity(alertToRegister)).thenReturn(entity);
        when(repository.configureAlert(entity)).thenReturn(true);

        // Act
        boolean result = alertConfigurationService.configureAlert(alertToRegister);

        // Assert
        verify(repository).configureAlert(entity);
        assertEquals(true, result);
    }
}
