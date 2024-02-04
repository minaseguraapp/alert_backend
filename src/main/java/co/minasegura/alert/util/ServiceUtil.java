package co.minasegura.alert.util;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.exception.NotValidParamException;
import co.minasegura.alert.properties.AlertExceptionProperties;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class ServiceUtil {

    private final AlertExceptionProperties exceptionProperties;

    public ServiceUtil(AlertExceptionProperties exceptionProperties) {
        this.exceptionProperties = exceptionProperties;
    }

    public String extractFilterParam(EnumMap<AlertConfigurationFilter, String> criteria,
                                     AlertConfigurationFilter paramToSearch, boolean required) {
        String param = criteria.get(paramToSearch);
        if (param == null && required) {
            throw new NotValidParamException(exceptionProperties.getMineParamNotPresentMessage());
        }
        return param;
    }

}
