package co.minasegura.alert.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Map;

@DynamoDbBean
public class AlertConfigurationEntity {
    private String mineId;
    private Long timestamp;
    private Map<String, String> alertConfigurationInfo;
    private String measurementType;

    public AlertConfigurationEntity() {
    }

    public AlertConfigurationEntity(String mineId, Long timestamp, Map<String, String> alertConfigurationInfo, String measurementType) {
        this.mineId = mineId;
        this.timestamp = timestamp;
        this.alertConfigurationInfo = alertConfigurationInfo;
        this.measurementType = measurementType;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("mineId")
    public String getMineId() {
        return mineId;
    }

    public void setMineId(String mineId) {
        this.mineId = mineId;
    }

    @DynamoDbAttribute("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbAttribute("alertConfigurationInfo")
    public Map<String, String> getAlertConfigurationInfo() {
        return alertConfigurationInfo;
    }

    public void setAlertConfigurationInfo(Map<String, String> alertConfigurationInfo) {
        this.alertConfigurationInfo = alertConfigurationInfo;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("measurementType")
    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }
}
