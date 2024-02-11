package co.minasegura.alert.service.impl;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.GetAlertConfigurationResponse;
import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.mapper.impl.AlertConfigurationMapper;
import co.minasegura.alert.model.alert.AlertConfiguration;
import co.minasegura.alert.repository.IAlertConfigurationRepository;
import co.minasegura.alert.service.IAlertConfigurationService;
import co.minasegura.alert.util.CommonsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.EnumMap;

@Service
public class AlertConfigurationService implements IAlertConfigurationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final IAlertConfigurationRepository repository;
    private final AlertConfigurationMapper mapper;

    private final CommonsUtil commonsUtil;

    public AlertConfigurationService(IAlertConfigurationRepository repository, AlertConfigurationMapper mapper,
                                     CommonsUtil commonsUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.commonsUtil = commonsUtil;
    }

    @Override
    public GetAlertConfigurationResponse getAlertConfiguration(EnumMap<AlertConfigurationFilter, String> criteria) {
        LOGGER.info("Get Alert Configuration Service Started with: [{}]", commonsUtil.toJson(criteria));

        List<AlertConfiguration> alertConfigurations = findAlertConfigurationInDatabase(
                criteria.get(AlertConfigurationFilter.MINE),
                criteria.get(AlertConfigurationFilter.MEASUREMENT_TYPE)
        );

        return new GetAlertConfigurationResponse(alertConfigurations);
    }

    private List<AlertConfiguration> findAlertConfigurationInDatabase(String mineId, String measurementType) {
        return this.repository.getAlertConfigurationEntities(mineId, measurementType).stream().map(mapper::entityToModel).toList();
    }

    @Override
    public boolean configureAlert(AlertConfiguration alertToRegister) {
        LOGGER.info("Configure Alert Service Started with: [{}]",
                commonsUtil.toJson(alertToRegister));

        final AlertConfigurationEntity entity = mapper.modelToEntity(alertToRegister);

        return this.repository.configureAlert(entity);
    }
}
