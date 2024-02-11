package co.minasegura.alert.events;

import co.minasegura.alert.model.measurement.Measurement;

public record MeasurementEvent(String eventType, Measurement measurement) {

}
