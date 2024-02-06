package co.minasegura.alert.repository;

import co.minasegura.alert.entity.AlertEntity;
import jakarta.annotation.Nonnull;
import java.util.List;

public interface IAlertRepository {

    List<AlertEntity> getAlertEntities(@Nonnull String mineId, @Nonnull String ZoneId,
        String measurementType);

    boolean createAlert(AlertEntity measurement);
}
