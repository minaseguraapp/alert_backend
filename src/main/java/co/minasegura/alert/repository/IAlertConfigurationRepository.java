package co.minasegura.alert.repository;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface IAlertConfigurationRepository {

    List<AlertConfigurationEntity> getAlertConfigurationEntities(String mineId, String measurementType);

    boolean configureAlert(AlertConfigurationEntity alertConfiguration);
}
