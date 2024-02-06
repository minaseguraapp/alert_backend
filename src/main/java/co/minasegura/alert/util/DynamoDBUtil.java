package co.minasegura.alert.util;

import co.minasegura.alert.properties.AlertProperties;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBUtil {

    private final AlertProperties properties;

    public DynamoDBUtil(AlertProperties properties) {
        this.properties = properties;
    }

    public Long getTimestamp(String measurementDateTimeType) {
        return Long.parseLong(measurementDateTimeType.split("#")[1]);
    }

    public String buildPartitionKey(String mine, String zoneId) {
        return String.format(properties.getPartitionKeyFormat(), getDefaultValue(mine),
            getDefaultValue(zoneId));
    }

    public String buildSortKey(String measurementType, Long timestamp) {
        return String.format(properties.getSortKeyFormat(), getDefaultValue(measurementType),
            getDefaultValue(timestamp));
    }

    public String getDefaultValue(Object value) {
        return Optional.ofNullable(value).map(Object::toString).filter(str -> !str.isBlank())
            .orElse("");
    }

}
