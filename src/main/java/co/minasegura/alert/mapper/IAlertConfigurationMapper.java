package co.minasegura.alert.mapper;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.model.alert.AlertConfiguration;

public interface IAlertConfigurationMapper {
    AlertConfiguration entityToModel(AlertConfigurationEntity entity);

    AlertConfigurationEntity modelToEntity(AlertConfiguration model);
}
