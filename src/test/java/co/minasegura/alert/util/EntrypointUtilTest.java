package co.minasegura.alert.util;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.model.AlertConfiguration;
import co.minasegura.alert.properties.AlertConfigurationProperties;
import jakarta.validation.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EntrypointUtilTest {

    private EntrypointUtil entrypointUtil;
    private AlertConfigurationProperties properties;
    private Validator validator;

    @Before
    public void setUp() {
        properties = mock(AlertConfigurationProperties.class);
        validator = mock(Validator.class);
        entrypointUtil = new EntrypointUtil(properties, validator);
    }

    @Test
    public void testGetAlertFilter() {
        // Arrange
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("mine", "123456");
        queryParams.put("measurementType", "METHANE");

        // Act
        EnumMap<AlertConfigurationFilter, String> filters = entrypointUtil.getAlertFilter(queryParams);

        // Assert
        assertTrue(filters.containsKey(AlertConfigurationFilter.MINE));
        assertTrue(filters.containsKey(AlertConfigurationFilter.MEASUREMENT_TYPE));
    }

    @Test
    public void testHasRequestMinimumCriteria() {
        // Arrange
        EnumMap<AlertConfigurationFilter, String> searchCriteria = new EnumMap<>(AlertConfigurationFilter.class);
        searchCriteria.put(AlertConfigurationFilter.MINE, "123456");
        searchCriteria.put(AlertConfigurationFilter.MEASUREMENT_TYPE, "METHANE");

        when(properties.getRequiredFilters()).thenReturn(Set.of(AlertConfigurationFilter.MINE));

        // Act
        boolean result = entrypointUtil.hasRequestMinimumCriteria(searchCriteria);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsMeasurementValid() {
        // Arrange
        AlertConfiguration alertConfiguration = new AlertConfiguration(
                123456789L, "mineId", null, new HashMap<>()
        );

        // Stubbing validator
        when(validator.validate(alertConfiguration)).thenReturn(
                Set.of() // empty set indicates no validation errors
        );

        // Act
        boolean result = entrypointUtil.isMeasurementValid(alertConfiguration);

        // Assert
        assertTrue(result);
    }
}
