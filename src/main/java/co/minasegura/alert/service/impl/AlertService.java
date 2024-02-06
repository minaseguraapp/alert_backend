package co.minasegura.alert.service.impl;

import co.minasegura.alert.dto.AlertQueryFilter;
import co.minasegura.alert.dto.GetAlertResponse;
import co.minasegura.alert.mapper.IAlertMapper;
import co.minasegura.alert.model.Alert;
import co.minasegura.alert.repository.IAlertRepository;
import co.minasegura.alert.service.IAlertService;
import co.minasegura.alert.util.CommonsUtil;
import java.util.EnumMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertService implements IAlertService {

    private final IAlertRepository repository;
    private final CommonsUtil commonsUtil;
    private final IAlertMapper alertMapper;

    public AlertService(IAlertRepository repository, CommonsUtil commonsUtil,
        IAlertMapper alertMapper) {
        this.repository = repository;
        this.commonsUtil = commonsUtil;
        this.alertMapper = alertMapper;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Override
    public GetAlertResponse getAlerts(EnumMap<AlertQueryFilter, String> criteria) {
        LOGGER.info("Get Alert Service Started with: [{}]", commonsUtil.toJson(criteria));

        List<Alert> measurements = findMeasurementInDatabase(
            criteria.get(AlertQueryFilter.MINE),
            criteria.get(AlertQueryFilter.ZONE_ID),
            criteria.get(AlertQueryFilter.MEASUREMENT_TYPE)
        );

        return new GetAlertResponse(measurements);
    }

    @Override
    public boolean createAlert(Alert measurementToRegister) {
        return false;
    }

    private List<Alert> findMeasurementInDatabase(String mineId, String zoneId,
        String measurementType) {
        return this.repository.getAlertEntities(mineId, zoneId, measurementType).stream()
            .map(alertMapper::entityToModel).toList();
    }
}
