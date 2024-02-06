package co.minasegura.alert.service;

import co.minasegura.alert.dto.AlertQueryFilter;
import co.minasegura.alert.dto.GetAlertResponse;
import co.minasegura.alert.model.Alert;
import java.util.EnumMap;

public interface IAlertService {

    GetAlertResponse getAlerts(EnumMap<AlertQueryFilter, String> criteria);

    boolean createAlert(Alert measurementToRegister);

}
