package co.minasegura.alert.dto;

import co.minasegura.alert.model.Alert;
import co.minasegura.alert.model.AlertConfiguration;
import java.util.List;

public record GetAlertResponse(List<Alert> alerts) {
}
