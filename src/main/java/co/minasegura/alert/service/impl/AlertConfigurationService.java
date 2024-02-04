package co.minasegura.alert.service.impl;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.GetAlertConfigurationResponse;
import co.minasegura.alert.model.AlertConfiguration;
import co.minasegura.alert.repository.IAlertConfigurationRepository;
import co.minasegura.alert.service.IAlertConfigurationService;
import co.minasegura.alert.util.CommonsUtil;
import co.minasegura.alert.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.EnumMap;

@Service
public class AlertConfigurationService implements IAlertConfigurationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final IAlertConfigurationRepository repository;
    private final ServiceUtil serviceUtil;
    private final CommonsUtil commonsUtil;

    public AlertConfigurationService(IAlertConfigurationRepository repository,
                                     ServiceUtil serviceUtil,
                                     CommonsUtil commonsUtil) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
        this.commonsUtil = commonsUtil;
    }

    @Override
    public GetAlertConfigurationResponse getAlertConfiguration(EnumMap<AlertConfigurationFilter, String> criteria) {
        return null;
    }

    @Override
    public boolean configureAlert(AlertConfiguration alertToRegister) {
        return true;
    }
}
