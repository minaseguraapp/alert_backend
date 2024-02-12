package co.minasegura.alert.service.impl;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.AlertQueryFilter;
import co.minasegura.alert.dto.GetAlertConfigurationResponse;
import co.minasegura.alert.dto.GetAlertResponse;
import co.minasegura.alert.entity.AlertEntity;
import co.minasegura.alert.mapper.IAlertMapper;
import co.minasegura.alert.model.alert.Alert;
import co.minasegura.alert.model.alert.AlertConfiguration;
import co.minasegura.alert.model.alert.AlertInfo;
import co.minasegura.alert.model.commons.MeasurementType;
import co.minasegura.alert.model.measurement.Measurement;
import co.minasegura.alert.repository.IAlertRepository;
import co.minasegura.alert.service.IAlertConfigurationService;
import co.minasegura.alert.service.IAlertService;
import co.minasegura.alert.service.IMeasurementFindingService;
import co.minasegura.alert.util.CommonsUtil;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertService implements IAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final CommonsUtil commonsUtil;

    private final IAlertRepository repository;

    private final IAlertMapper alertMapper;

    private final IMeasurementFindingService findingService;
    private final IAlertConfigurationService configurationService;

    public AlertService(IAlertRepository repository, CommonsUtil commonsUtil,
        IAlertMapper alertMapper, IMeasurementFindingService findingService,
        IAlertConfigurationService configurationService) {
        this.repository = repository;
        this.commonsUtil = commonsUtil;
        this.alertMapper = alertMapper;
        this.findingService = findingService;
        this.configurationService = configurationService;
    }

    @Override
    public GetAlertResponse getAlerts(EnumMap<AlertQueryFilter, String> criteria) {
        LOGGER.info("Get Alert Service Started with: [{}]", commonsUtil.toJson(criteria));
        return new GetAlertResponse(findMeasurementInDatabase(
            criteria.get(AlertQueryFilter.MINE),
            criteria.get(AlertQueryFilter.ZONE_ID),
            criteria.get(AlertQueryFilter.MEASUREMENT_TYPE)
        ));
    }

    @Override
    public Alert processMeasurementAlert(Measurement measurement) {

        final MeasurementType measurementType = measurement.measurementType();

        final var configurationCriteria = new EnumMap<AlertConfigurationFilter, String>(
            AlertConfigurationFilter.class);

        configurationCriteria.put(AlertConfigurationFilter.MINE, measurement.zone().mine().id());
        configurationCriteria.put(AlertConfigurationFilter.MEASUREMENT_TYPE,
            measurementType.name());

        final GetAlertConfigurationResponse measurementConfiguration = configurationService.getAlertConfiguration(
            configurationCriteria);

        if (measurementConfiguration == null
            || measurementConfiguration.alertConfigurations() == null) {
            LOGGER.info("Alert Configuration not found for : [{}]",
                commonsUtil.toJson(measurement));
            return null;
        }

        final Optional<AlertConfiguration> configuration = measurementConfiguration.alertConfigurations()
            .stream()
            .filter(
                alertConfiguration -> measurementType.equals(alertConfiguration.measurementType()))
            .findFirst();

        if (configuration.isEmpty()) {
            LOGGER.info("Alert Configuration not found for : [{}]",
                commonsUtil.toJson(measurement));
            return null;
        }

        LOGGER.info("General Configuration found : [{}]",
            commonsUtil.toJson(configuration.get()));

        final List<AlertInfo> alertInfo = findingService.getMeasurementThresholdFindings(
            measurementType, measurement.measurementInfo(), configuration.get().thresholdInfo());

        LOGGER.info("Alerts created : [{}]",
            commonsUtil.toJson(alertInfo));

        Optional<Alert> alertToReturn= Optional.ofNullable(alertInfo)
            .filter(alertInfos -> !alertInfos.isEmpty())
            .map(alertInfos-> alertMapper.measurementToAlert(measurement, alertInfos));

        alertToReturn.ifPresent(this::createAlert);

        return alertToReturn.orElse(null);
    }

    private boolean createAlert(Alert alertToRegister) {
        final AlertEntity entity = alertMapper.modelToEntity(alertToRegister);
        return this.repository.createAlert(entity);
    }

    private List<Alert> findMeasurementInDatabase(String mineId, String zoneId,
        String measurementType) {
        return this.repository.getAlertEntities(mineId, zoneId, measurementType).stream()
            .map(alertMapper::entityToModel).toList();
    }
}
