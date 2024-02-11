package co.minasegura.alert.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import co.minasegura.alert.dto.alert.CoalThresholdTypes;
import co.minasegura.alert.model.alert.AlertInfo;
import co.minasegura.alert.model.commons.MeasurementType;
import co.minasegura.alert.service.impl.MeasurementFindingService;
import co.minasegura.alert.util.CommonsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class MeasurementFindingServiceTest {

    private MeasurementFindingService findingService;

    @Before
    public void setUp() {
        findingService = new MeasurementFindingService(new CommonsUtil(new ObjectMapper()));
    }

    @Test
    public void testGetMeasurementThresholdFindings_Methane_ShouldReturnEmpty() {
        final MeasurementType measurementType = MeasurementType.METHANE;

        final Map<String, String> measurementInfo = new HashMap<>();
        measurementInfo.put("methaneLevel", "0.005");
        measurementInfo.put("measurementSite", "MINING_OPERATIONS");

        final Map<String, String> thresholdInfo = getMethaneThresholdData();

        List<AlertInfo> result = findingService.getMeasurementThresholdFindings(measurementType,
            measurementInfo, thresholdInfo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetMeasurementThresholdFindings_Methane_ShouldReturnFinding() {
        final MeasurementType measurementType = MeasurementType.METHANE;

        final Map<String, String> measurementInfo = new HashMap<>();
        measurementInfo.put("methaneLevel", "1");
        measurementInfo.put("measurementSite", "MINING_OPERATIONS");

        final Map<String, String> thresholdInfo = getMethaneThresholdData();

        List<AlertInfo> result = findingService.getMeasurementThresholdFindings(measurementType,
            measurementInfo, thresholdInfo);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("MINING_OPERATIONS", result.get(0).thresholdName());

    }

    @Test
    public void testGetMeasurementThresholdFindings_COALDUST_ShouldReturnEmpty() {
        final MeasurementType measurementType = MeasurementType.COAL_DUST;

        final Map<String, String> measurementInfo = new HashMap<>();
        measurementInfo.put("dustLevel", "2");
        measurementInfo.put("particleSize", "3");

        final Map<String, String> thresholdInfo = getCoalDustThresholdData();

        List<AlertInfo> result = findingService.getMeasurementThresholdFindings(measurementType,
            measurementInfo, thresholdInfo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetMeasurementThresholdFindings_COALDUST_ShouldReturnFinding() {
        final MeasurementType measurementType = MeasurementType.COAL_DUST;

        final Map<String, String> measurementInfo = new HashMap<>();
        measurementInfo.put("dustLevel", "10");
        measurementInfo.put("particleSize", "10");

        final Map<String, String> thresholdInfo = getCoalDustThresholdData();

        List<AlertInfo> result = findingService.getMeasurementThresholdFindings(measurementType,
            measurementInfo, thresholdInfo);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(CoalThresholdTypes.COAL_DUST_LEVEL.name(), result.get(0).thresholdName());
        assertEquals(CoalThresholdTypes.PARTICLE_DUST_SIZE.name(), result.get(1).thresholdName());

    }

    private Map<String, String> getMethaneThresholdData() {
        final Map<String, String> thresholdInfo = new HashMap<>();
        thresholdInfo.put("miningOperationsMaxMethaneLevel", "1.0");
        thresholdInfo.put("mainAirReturnMaxMethaneLevel", "1.0");
        thresholdInfo.put("airReturnFromStallsMaxMethaneLevel", "1.0");
        thresholdInfo.put("airReturnFromPrepAndDevMaxMethaneLevel", "1.5");
        return thresholdInfo;
    }


    private Map<String, String> getCoalDustThresholdData() {
        final Map<String, String> thresholdInfo = new HashMap<>();
        thresholdInfo.put("maxDustLevel", "4.0");
        thresholdInfo.put("maxParticleSize", "5.0");
        return thresholdInfo;
    }

}
