package co.minasegura.alert.service;

import co.minasegura.alert.model.alert.AlertInfo;
import co.minasegura.alert.model.commons.MeasurementType;
import java.util.List;
import java.util.Map;

public interface IMeasurementFindingService {

    List<AlertInfo> getMeasurementThresholdFindings(
        MeasurementType measurementType, Map<String, String> measurementInfo,
        Map<String, String> thresholdInfo);

}
