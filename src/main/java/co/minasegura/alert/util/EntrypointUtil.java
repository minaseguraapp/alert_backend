package co.minasegura.alert.util;

import co.minasegura.alert.dto.AlertFilter;
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

    public EnumMap<AlertFilter, String> getAlertFilter(
        Map<String, String> queryParams) {

        final Map<String, AlertFilter> invertedFilterMap = Stream.of(
                        AlertFilter.values())
            .collect(Collectors.toMap(AlertFilter::getFilter, filter -> filter));

        final EnumMap<AlertFilter, String> filters = new EnumMap<>(AlertFilter.class);

        queryParams.entrySet().stream()
            .filter(entry -> invertedFilterMap.containsKey(entry.getKey()))
            .forEach(entry -> filters.put(invertedFilterMap.get(entry.getKey()), entry.getValue()));

        return filters;
    }

    public boolean hasRequestMinimumCriteria(EnumMap<AlertFilter, String> searchCriteria) {
        return properties.getRequiredFilters().stream().allMatch(searchCriteria::containsKey);
    }

    public boolean isMeasurementValid(AlertConfiguration alertConfiguration) {
        return alertConfiguration != null && validator.validate(alertConfiguration).isEmpty();
    }

}
