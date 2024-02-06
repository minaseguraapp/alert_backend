package co.minasegura.alert.properties;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.AlertQueryFilter;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "alert")
public class AlertProperties {
    private Set<AlertQueryFilter> requiredFilters;
    private String partitionKeyFormat;
    private String sortKeyFormat;


    public Set<AlertQueryFilter> getRequiredFilters() {
        return requiredFilters;
    }

    public void setRequiredFilters(Set<AlertQueryFilter> requiredFilters) {
        this.requiredFilters = requiredFilters;
    }

    public String getPartitionKeyFormat() {
        return partitionKeyFormat;
    }

    public void setPartitionKeyFormat(String partitionKeyFormat) {
        this.partitionKeyFormat = partitionKeyFormat;
    }

    public String getSortKeyFormat() {
        return sortKeyFormat;
    }

    public void setSortKeyFormat(String sortKeyFormat) {
        this.sortKeyFormat = sortKeyFormat;
    }
}
