package co.minasegura.alert.mapper;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.entity.AlertEntity;
import co.minasegura.alert.model.Alert;
import co.minasegura.alert.model.AlertConfiguration;

public interface IAlertMapper {
    Alert entityToModel(AlertEntity entity);

    AlertEntity modelToEntity(Alert model);
}
