package co.minasegura.alert.mapper;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.mapper.impl.AlertConfigurationMapper;
import co.minasegura.alert.model.AlertConfiguration;
import co.minasegura.alert.model.MeasurementType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AlertConfigurationMapperTest {

    private AlertConfigurationMapper alertConfigurationMapper;

    @Before
    public void setUp() {
        alertConfigurationMapper = new AlertConfigurationMapper();
    }


    @Test
    public void testEntityToModel() {
        // Arrange
        AlertConfigurationEntity entity = new AlertConfigurationEntity(
                "123456",
                123456789L,
                new HashMap<>(),
                "COAL_DUST"
        );

        // Act
        AlertConfiguration alertConfiguration = alertConfigurationMapper.entityToModel(entity);

        // Assert
        assertEquals(123456789L, alertConfiguration.timestamp().longValue());
        assertEquals("123456", alertConfiguration.mineId());
        assertEquals(MeasurementType.COAL_DUST, alertConfiguration.measurementType());
    }

    @Test
    public void testModelToEntity() {
        // Arrange
        AlertConfiguration model = new AlertConfiguration(
                123456789L,
                "123456",
                MeasurementType.COAL_DUST,
                new HashMap<>()
        );

        // Act
        AlertConfigurationEntity entity = alertConfigurationMapper.modelToEntity(model);

        // Assert
        assertEquals(123456789L, entity.getTimestamp().longValue());
        assertEquals("123456", entity.getMineId());
        assertEquals("COAL_DUST", entity.getMeasurementType());
    }
}