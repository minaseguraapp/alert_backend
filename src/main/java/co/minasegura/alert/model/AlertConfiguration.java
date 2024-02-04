package co.minasegura.alert.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record AlertConfiguration(
        @NotNull Long timestamp,
        @NotNull Mine mine,
        @NotNull @Valid MeasurementType measurementType,
        @NotNull Map<String, String> thresholdInfo) {
}
