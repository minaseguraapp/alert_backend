package co.minasegura.alert.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class AlertInfoEntity {

    private String thresholdName;
    private String valueExpected;
    private String valueFound;

    public AlertInfoEntity() {
    }

    public AlertInfoEntity(String thresholdName, String valueExpected, String valueFound) {
        this.thresholdName = thresholdName;
        this.valueExpected = valueExpected;
        this.valueFound = valueFound;
    }

    @DynamoDbAttribute("thresholdName")
    public String getThresholdName() {
        return thresholdName;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName;
    }

    @DynamoDbAttribute("valueExpected")
    public String getValueExpected() {
        return valueExpected;
    }

    public void setValueExpected(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    @DynamoDbAttribute("valueFound")
    public String getValueFound() {
        return valueFound;
    }

    public void setValueFound(String valueFound) {
        this.valueFound = valueFound;
    }
}
