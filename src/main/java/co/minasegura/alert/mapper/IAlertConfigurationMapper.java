package co.minasegura.alert.mapper;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.model.AlertConfiguration;

public interface IAlertConfigurationMapper {
    AlertConfiguration entityToModel(AlertConfigurationEntity entity);

    AlertConfigurationEntity modelToEntity(AlertConfiguration model);
}
