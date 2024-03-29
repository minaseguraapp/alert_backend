package co.minasegura.alert.properties;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "alert.configuration")
public class AlertConfigurationProperties {
    private Set<AlertConfigurationFilter> requiredFilters;

    public Set<AlertConfigurationFilter> getRequiredFilters() {
        return requiredFilters;
    }

    public void setRequiredFilters(Set<AlertConfigurationFilter> requiredFilters) {
        this.requiredFilters = requiredFilters;
    }
}
