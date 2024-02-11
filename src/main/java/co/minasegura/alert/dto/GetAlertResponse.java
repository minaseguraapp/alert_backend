package co.minasegura.alert.dto;

import co.minasegura.alert.model.alert.Alert;
import java.util.List;

public record GetAlertResponse(List<Alert> alerts) {
}
