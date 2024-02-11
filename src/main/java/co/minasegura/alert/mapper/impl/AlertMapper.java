package co.minasegura.alert.mapper.impl;

import co.minasegura.alert.entity.AlertEntity;
import co.minasegura.alert.entity.AlertInfoEntity;
import co.minasegura.alert.mapper.IAlertMapper;
import co.minasegura.alert.model.alert.Alert;
import co.minasegura.alert.model.alert.AlertInfo;
import co.minasegura.alert.model.measurement.Measurement;
import co.minasegura.alert.util.DynamoDBUtil;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper implements IAlertMapper {

    private final DynamoDBUtil dbUtil;

    public AlertMapper(DynamoDBUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public Alert entityToModel(AlertEntity entity) {

        return new Alert(
            entity.getMineId(),
            entity.getZoneId(),
            entity.getMeasurementType(),
            entity.getAlertTimestamp(),
            entity.getMeasureTimestamp(),
            entity.getMeasurementInfo(),
            entity.getAlertInfo().stream().map(
                alertInfoEntity -> new AlertInfo(alertInfoEntity.getThresholdName(),
                    alertInfoEntity.getValueExpected(), alertInfoEntity.getValueFound())).toList()
        );
    }

    @Override
    public AlertEntity modelToEntity(Alert model) {
        return new AlertEntity(
            dbUtil.buildPartitionKey(model.mineId(), model.zoneId()),
            dbUtil.buildSortKey(model.measurementType(), model.alertTimestamp()),
            model.mineId(),
            model.zoneId(),
            model.measurementType(),
            model.alertTimestamp(),
            model.measureTimestamp(),
            model.measurementInfo(),
            model.alertInfo().stream().map(
                alertInfo -> new AlertInfoEntity(alertInfo.thresholdName(),
                    alertInfo.valueExpected(),
                    alertInfo.valueFound())).toList()
        );
    }

    @Override
    public Alert measurementToAlert(Measurement measurement, List<AlertInfo> alertInfo) {
        return null;
    }
}
