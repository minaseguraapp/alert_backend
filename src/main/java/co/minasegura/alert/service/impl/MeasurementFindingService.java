package co.minasegura.alert.service.impl;

import co.minasegura.alert.dto.alert.CoalDustAlertConfiguration;
import co.minasegura.alert.dto.alert.MethaneAlertConfiguration;
import co.minasegura.alert.dto.measurement.CoalDustMeasurementInfo;
import co.minasegura.alert.dto.measurement.CoalThresholdTypes;
import co.minasegura.alert.dto.measurement.MethaneMeasurementInfo;
import co.minasegura.alert.model.alert.AlertInfo;
import co.minasegura.alert.model.commons.MeasurementType;
import co.minasegura.alert.service.IMeasurementFindingService;
import co.minasegura.alert.util.CommonsUtil;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MeasurementFindingService implements IMeasurementFindingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final CommonsUtil commonsUtil;
    private final EnumMap<MeasurementType, BiFunction<Map<String, String>, Map<String, String>, List<AlertInfo>>> findingsProcessors;


    public MeasurementFindingService(CommonsUtil commonsUtil) {
        this.commonsUtil = commonsUtil;
        findingsProcessors = new EnumMap<>(MeasurementType.class);
        findingsProcessors.put(MeasurementType.METHANE, this::getFindingsForMethane);
        findingsProcessors.put(MeasurementType.COAL_DUST, this::getFindingsForCoalDust);
    }

    @Override

    public List<AlertInfo> getMeasurementThresholdFindings(MeasurementType measurementType,
        Map<String, String> measurementInfo, Map<String, String> thresholdInfo) {

        return this.findingsProcessors.get(measurementType).apply(measurementInfo, thresholdInfo);
    }

    private List<AlertInfo> getFindingsForMethane(Map<String, String> measurementInfo,
        Map<String, String> thresholdInfo) {

        final List<AlertInfo> alertInfo = new ArrayList<>();

        try {
            MethaneMeasurementInfo methaneMeasurement = commonsUtil.mapToObject(measurementInfo,
                MethaneMeasurementInfo.class);
            MethaneAlertConfiguration methaneConfiguration = commonsUtil.mapToObject(thresholdInfo,
                MethaneAlertConfiguration.class);

            String maxMethaneLevel = switch (methaneMeasurement.measurementSite()) {
                case MINING_OPERATIONS -> methaneConfiguration.miningOperationsMaxMethaneLevel();
                case MAIN_AIR_RETURN -> methaneConfiguration.mainAirReturnMaxMethaneLevel();
                case AIR_RETURN_FROM_STALLS ->
                    methaneConfiguration.airReturnFromStallsMaxMethaneLevel();
                case AIR_RETURN_PREP_DEV ->
                    methaneConfiguration.airReturnFromPrepAndDevMaxMethaneLevel();
            };

            double maxMethaneLevelValue = Double.parseDouble(maxMethaneLevel);
            double currentMethaneLevel = Double.parseDouble(methaneMeasurement.methaneLevel());

            if (currentMethaneLevel >= maxMethaneLevelValue) {
                alertInfo.add(new AlertInfo(methaneMeasurement.measurementSite().name(),
                    Double.toString(maxMethaneLevelValue), Double.toString(currentMethaneLevel)));
            }

        } catch (Exception ex) {
            LOGGER.error("An error occurred generated processing methane analysis", ex);
        }
        return alertInfo;
    }


    private List<AlertInfo> getFindingsForCoalDust(Map<String, String> coalInfo,
        Map<String, String> thresholdInfo) {

        final List<AlertInfo> alertInfo = new ArrayList<>();

        try {
            CoalDustMeasurementInfo coalDustMeasurement = commonsUtil.mapToObject(coalInfo,
                CoalDustMeasurementInfo.class);
            CoalDustAlertConfiguration coalDustConfiguration = commonsUtil.mapToObject(
                thresholdInfo,
                CoalDustAlertConfiguration.class);

            double maxDustLevel = Double.parseDouble(coalDustConfiguration.maxDustLevel());
            double currentDustLevel = Double.parseDouble(coalDustMeasurement.dustLevel());

            if (currentDustLevel >= maxDustLevel) {
                alertInfo.add(new AlertInfo(CoalThresholdTypes.COAL_DUST_LEVEL.name(),
                    Double.toString(maxDustLevel), Double.toString(currentDustLevel)));
            }

            double maxParticleSize = Double.parseDouble(coalDustConfiguration.maxParticleSize());
            double currentParticleSize = Double.parseDouble(coalDustMeasurement.particleSize());

            if (currentParticleSize >= maxParticleSize) {
                alertInfo.add(new AlertInfo(CoalThresholdTypes.PARTICLE_DUST_SIZE.name(),
                    Double.toString(maxParticleSize), Double.toString(currentParticleSize)));
            }

        } catch (Exception ex) {
            LOGGER.error("An error occurred generated processing coal dust analysis", ex);
        }

        return alertInfo;
    }


}
