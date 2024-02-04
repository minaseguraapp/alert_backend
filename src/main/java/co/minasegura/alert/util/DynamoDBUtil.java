package co.minasegura.alert.util;

import co.minasegura.measurement.properties.MeasurementProperties;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBUtil {

    private final AlertProperties properties;

    public DynamoDBUtil(AlertProperties properties) {
        this.properties = properties;
    }

}
