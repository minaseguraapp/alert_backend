package co.minasegura.alert.dto;

import co.minasegura.alert.model.AlertConfiguration;

import java.util.List;

public record GetAlertConfigurationResponse(List<AlertConfiguration> alertConfigurations) {
}
