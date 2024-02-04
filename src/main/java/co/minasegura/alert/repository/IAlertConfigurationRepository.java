package co.minasegura.alert.repository;

import co.minasegura.measurement.entity.MeasurementEntity;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface IAlertConfigurationRepository {

    List<AlertConfigurationEntity> setAlertConfigurationEntity(@Nonnull String mineId, @Nonnull String ZoneId,
        String measurementType);

    boolean createMeasurement(AlertConfigurationEntity alertConfiguration);
}
