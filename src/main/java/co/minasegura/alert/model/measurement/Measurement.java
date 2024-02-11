package co.minasegura.alert.model.measurement;

import co.minasegura.alert.model.commons.MeasurementType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record Measurement(
    @NotNull Long timestamp,
    @NotNull @Valid MeasurementType measurementType,
    @NotNull @Valid Zone zone,
    @NotNull Map<String, String> measurementInfo) {

}