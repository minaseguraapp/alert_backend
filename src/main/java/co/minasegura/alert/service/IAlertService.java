package co.minasegura.alert.service;

import co.minasegura.alert.dto.AlertQueryFilter;
import co.minasegura.alert.dto.GetAlertResponse;
import co.minasegura.alert.model.alert.Alert;
import co.minasegura.alert.model.measurement.Measurement;
import java.util.EnumMap;

public interface IAlertService {

    GetAlertResponse getAlerts(EnumMap<AlertQueryFilter, String> criteria);

    Alert processMeasurementAlert(Measurement measurement);

}
