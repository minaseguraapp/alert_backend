package co.minasegura.alert.repository.impl;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.repository.IAlertConfigurationRepository;
import jakarta.annotation.Nonnull;

import java.util.List;

public class AlertConfigurationDynamoDBRepository implements IAlertConfigurationRepository {
    @Override
    public List<AlertConfigurationEntity> getAlertConfigurationEntities(@Nonnull String mineId, String measurementType) {
        return null;
    }

    @Override
    public boolean configureAlert(AlertConfigurationEntity alertConfiguration) {
        return false;
    }
}
