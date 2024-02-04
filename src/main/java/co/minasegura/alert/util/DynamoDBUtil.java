package co.minasegura.alert.util;

import co.minasegura.alert.properties.AlertConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DynamoDBUtil {

    private final AlertConfigurationProperties properties;

    public DynamoDBUtil(AlertConfigurationProperties properties) {
        this.properties = properties;
    }

    public String getDefaultValue(Object value) {
        return Optional.ofNullable(value).map(Object::toString).filter(str -> !str.isBlank())
                .orElse("");
    }
}
