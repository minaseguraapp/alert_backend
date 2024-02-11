package co.minasegura.alert.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.model.alert.AlertConfiguration;
import co.minasegura.alert.properties.AlertConfigurationProperties;
import co.minasegura.alert.properties.AlertProperties;
import jakarta.validation.Validator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class EntrypointUtilTest {

    private EntrypointUtil entrypointUtil;
    private AlertConfigurationProperties properties;
    private AlertProperties alertProperties;
    private Validator validator;

    @Before
    public void setUp() {
        properties = mock(AlertConfigurationProperties.class);
        validator = mock(Validator.class);
        alertProperties = mock(AlertProperties.class);
        entrypointUtil = new EntrypointUtil(properties, validator, alertProperties);
    }

    @Test
    public void testGetAlertFilter() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("mine", "123456");
        queryParams.put("measurementType", "METHANE");

        EnumMap<AlertConfigurationFilter, String> filters = entrypointUtil.getAlertConfigFilter(
            queryParams);

        assertTrue(filters.containsKey(AlertConfigurationFilter.MINE));
        assertTrue(filters.containsKey(AlertConfigurationFilter.MEASUREMENT_TYPE));
    }

    @Test
    public void testHasRequestMinimumCriteria() {
        // Arrange
        EnumMap<AlertConfigurationFilter, String> searchCriteria = new EnumMap<>(
            AlertConfigurationFilter.class);
        searchCriteria.put(AlertConfigurationFilter.MINE, "123456");
        searchCriteria.put(AlertConfigurationFilter.MEASUREMENT_TYPE, "METHANE");

        when(properties.getRequiredFilters()).thenReturn(Set.of(AlertConfigurationFilter.MINE));

        boolean result = entrypointUtil.hasRequestMinimumCriteria(searchCriteria);

        assertTrue(result);
    }

    @Test
    public void testIsMeasurementValid() {
        AlertConfiguration alertConfiguration = new AlertConfiguration(
            123456789L, "mineId", null, new HashMap<>()
        );

        when(validator.validate(alertConfiguration)).thenReturn(
            Set.of()
        );

        boolean result = entrypointUtil.isAlertConfigurationValid(alertConfiguration);

        assertTrue(result);
    }
}
