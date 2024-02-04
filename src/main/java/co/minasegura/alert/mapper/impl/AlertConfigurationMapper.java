package co.minasegura.alert.mapper.impl;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.mapper.IAlertConfigurationMapper;
import co.minasegura.alert.model.AlertConfiguration;
import co.minasegura.alert.model.MeasurementType;
import co.minasegura.alert.model.Mine;

public class AlertConfigurationMapper implements IAlertConfigurationMapper {

    @Override
    public AlertConfiguration entityToModel(AlertConfigurationEntity entity) {
        Mine mine = new Mine(entity.getMineId());
        MeasurementType measurementType = MeasurementType.valueOf(entity.getMeasurementType());

        return new AlertConfiguration(entity.getTimestamp(), mine, measurementType, entity.getAlertConfigurationInfo());
    }

    @Override
    public AlertConfigurationEntity modelToEntity(AlertConfiguration model) {
        return null;
    }
}
