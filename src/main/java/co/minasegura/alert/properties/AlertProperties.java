package co.minasegura.alert.properties;

import co.minasegura.alert.dto.AlertFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "alert")
public class AlertProperties {
    private Set<AlertFilter> requiredFilters;
}
