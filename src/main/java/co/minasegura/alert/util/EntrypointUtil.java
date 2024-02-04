package co.minasegura.alert.util;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.model.AlertConfiguration;
import co.minasegura.alert.properties.AlertProperties;
import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.properties.MeasurementProperties;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EntrypointUtil {

    private final AlertProperties properties;
    private final Validator validator;

    public EntrypointUtil(AlertProperties properties, Validator validator) {
        this.properties = properties;
        this.validator= validator;
    }

    public EnumMap<AlertConfigurationFilter, String> getAlertFilter(
        Map<String, String> queryParams) {

        final Map<String, AlertConfigurationFilter> invertedFilterMap = Stream.of(
                        AlertConfigurationFilter.values())
            .collect(Collectors.toMap(AlertConfigurationFilter::getFilter, filter -> filter));

        final EnumMap<AlertConfigurationFilter, String> filters = new EnumMap<>(AlertConfigurationFilter.class);

        queryParams.entrySet().stream()
            .filter(entry -> invertedFilterMap.containsKey(entry.getKey()))
            .forEach(entry -> filters.put(invertedFilterMap.get(entry.getKey()), entry.getValue()));

        return filters;
    }

    public boolean hasRequestMinimumCriteria(EnumMap<AlertConfigurationFilter, String> searchCriteria) {
        return properties.getRequiredFilters().stream().allMatch(searchCriteria::containsKey);
    }

    public boolean isMeasurementValid(AlertConfiguration alertConfiguration) {
        return alertConfiguration != null && validator.validate(alertConfiguration).isEmpty();
    }

}
