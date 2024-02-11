package co.minasegura.alert.mapper;

import static org.junit.Assert.assertEquals;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.mapper.impl.AlertConfigurationMapper;
import co.minasegura.alert.model.alert.AlertConfiguration;
import co.minasegura.alert.model.commons.MeasurementType;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class AlertConfigurationMapperTest {

    private AlertConfigurationMapper alertConfigurationMapper;

    @Before
    public void setUp() {
        alertConfigurationMapper = new AlertConfigurationMapper();
    }


    @Test
    public void testEntityToModel() {
        AlertConfigurationEntity entity = new AlertConfigurationEntity(
            "123456",
            123456789L,
            new HashMap<>(),
            "COAL_DUST"
        );

        AlertConfiguration alertConfiguration = alertConfigurationMapper.entityToModel(entity);

        assertEquals(123456789L, alertConfiguration.timestamp().longValue());
        assertEquals("123456", alertConfiguration.mineId());
        assertEquals(MeasurementType.COAL_DUST, alertConfiguration.measurementType());
    }

    @Test
    public void testModelToEntity() {
        AlertConfiguration model = new AlertConfiguration(
            123456789L,
            "123456",
            MeasurementType.COAL_DUST,
            new HashMap<>()
        );

        AlertConfigurationEntity entity = alertConfigurationMapper.modelToEntity(model);

        assertEquals(123456789L, entity.getTimestamp().longValue());
        assertEquals("123456", entity.getMineId());
        assertEquals("COAL_DUST", entity.getMeasurementType());
    }
}