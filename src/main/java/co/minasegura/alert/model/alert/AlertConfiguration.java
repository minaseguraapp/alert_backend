package co.minasegura.alert.model.alert;

import co.minasegura.alert.model.commons.MeasurementType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record AlertConfiguration(
        @NotNull Long timestamp,
        @NotNull String mineId,
        @NotNull @Valid MeasurementType measurementType,
        @NotNull Map<String, String> thresholdInfo) {
}
