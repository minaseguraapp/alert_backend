package co.minasegura.alert.service;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.GetAlertConfigurationResponse;
import co.minasegura.alert.model.AlertConfiguration;

import java.util.EnumMap;

public interface IAlertConfigurationService {
    GetAlertConfigurationResponse getAlertConfiguration(EnumMap<AlertConfigurationFilter,String> criteria);

    boolean configureAlert(AlertConfiguration alertToRegister);
}
