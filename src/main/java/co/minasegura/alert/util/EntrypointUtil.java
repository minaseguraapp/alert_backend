package co.minasegura.alert.util;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.AlertQueryFilter;
import co.minasegura.alert.model.alert.Alert;
import co.minasegura.alert.model.alert.AlertConfiguration;
import co.minasegura.alert.properties.AlertConfigurationProperties;
import co.minasegura.alert.properties.AlertProperties;
import jakarta.validation.Validator;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class EntrypointUtil {

    private final AlertConfigurationProperties properties;
    private final AlertProperties alertProperties;

    private final Validator validator;

    public EntrypointUtil(AlertConfigurationProperties properties, Validator validator,
        AlertProperties alertProperties) {
        this.properties = properties;
        this.validator = validator;
        this.alertProperties= alertProperties;
    }

    public EnumMap<AlertConfigurationFilter, String> getAlertConfigFilter(
        Map<String, String> queryParams) {

        final Map<String, AlertConfigurationFilter> invertedFilterMap = Stream.of(
                AlertConfigurationFilter.values())
            .collect(Collectors.toMap(AlertConfigurationFilter::getFilter, filter -> filter));

        final EnumMap<AlertConfigurationFilter, String> filters = new EnumMap<>(
            AlertConfigurationFilter.class);

        queryParams.entrySet().stream()
            .filter(entry -> invertedFilterMap.containsKey(entry.getKey()))
            .forEach(entry -> filters.put(invertedFilterMap.get(entry.getKey()), entry.getValue()));

        return filters;
    }

    public EnumMap<AlertQueryFilter, String> getAlertFilter(
        Map<String, String> queryParams) {

        final Map<String, AlertQueryFilter> invertedFilterMap = Stream.of(
                AlertQueryFilter.values())
            .collect(Collectors.toMap(AlertQueryFilter::getFilter, filter -> filter));

        final EnumMap<AlertQueryFilter, String> filters = new EnumMap<>(AlertQueryFilter.class);

        queryParams.entrySet().stream()
            .filter(entry -> invertedFilterMap.containsKey(entry.getKey()))
            .forEach(entry -> filters.put(invertedFilterMap.get(entry.getKey()), entry.getValue()));

        return filters;
    }

    public boolean hasRequestMinimumCriteria(
        EnumMap<AlertConfigurationFilter, String> searchCriteria) {
        return properties.getRequiredFilters().stream().allMatch(searchCriteria::containsKey);
    }

    public boolean hasAlertRequestMinimumCriteria(
        EnumMap<AlertQueryFilter, String> searchCriteria) {
        return alertProperties.getRequiredFilters().stream().allMatch(searchCriteria::containsKey);
    }


    public boolean isAlertConfigurationValid(AlertConfiguration alertConfiguration) {
        return alertConfiguration != null && validator.validate(alertConfiguration).isEmpty();
    }

    public boolean isAlertValid(Alert alertConfiguration) {
        return alertConfiguration != null && validator.validate(alertConfiguration).isEmpty();
    }
}
