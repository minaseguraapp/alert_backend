package co.minasegura.alert.mapper;

import co.minasegura.alert.entity.AlertEntity;
import co.minasegura.alert.model.alert.Alert;
import co.minasegura.alert.model.alert.AlertInfo;
import co.minasegura.alert.model.measurement.Measurement;
import java.util.List;

public interface IAlertMapper {
    Alert entityToModel(AlertEntity entity);

    AlertEntity modelToEntity(Alert model);

    Alert measurementToAlert(Measurement measurement, List<AlertInfo> alertInfo);
}
