package co.minasegura.alert.dto.measurement;

import co.minasegura.alert.dto.alert.MethaneThresholdTypes;

public record MethaneMeasurementInfo(String methaneLevel, MethaneThresholdTypes measurementSite) {
}
