package co.minasegura.alert.service.impl;

import co.minasegura.alert.service.IAlertConfigurationService;
import co.minasegura.alert.util.CommonsUtil;
import co.minasegura.alert.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertConfigurationService implements IAlertConfigurationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final IAlertConfigurationRepository repository;
    private final ServiceUtil serviceUtil;
    private final AlertConfigurationMapper mapper;
    private final CommonsUtil commonsUtil;

    public AlertConfigurationService(IAlertConfigurationRepository repository,
                        ServiceUtil serviceUtil,
                        AlertConfigurationMapper mapper,
                        CommonsUtil commonsUtil) {
        this.repository = repository;
        this.serviceUtil = serviceUtil;
        this.mapper = mapper;
        this.commonsUtil = commonsUtil;
    }
}
