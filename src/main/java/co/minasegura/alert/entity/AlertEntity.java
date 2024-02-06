package co.minasegura.alert.entity;

import java.util.List;
import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class AlertEntity {

    private String mineZoneId;
    private String measurementTypeTimestamp;
    private String mineId;
    private String zoneId;
    private String measurementType;
    private Long alertTimestamp;
    private Long measureTimestamp;
    private Map<String, String> measurementInfo;
    private List<AlertInfoEntity> alertInfo;

    public AlertEntity() {
    }

    public AlertEntity(
        String mineZoneId,
        String measurementTypeTimestamp,
        String mineId,
        String zoneId,
        String measurementType,
        Long alertTimestamp,
        Long measureTimestamp,
        Map<String, String> measurementInfo,
        List<AlertInfoEntity> alertInfo
    ) {
        this.mineZoneId = mineZoneId;
        this.measurementTypeTimestamp = measurementTypeTimestamp;
        this.mineId = mineId;
        this.zoneId = zoneId;
        this.measurementType = measurementType;
        this.alertTimestamp = alertTimestamp;
        this.measureTimestamp = measureTimestamp;
        this.measurementInfo = measurementInfo;
        this.alertInfo = alertInfo;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("mineZoneId")
    public String getMineZoneId() {
        return mineZoneId;
    }

    public void setMineZoneId(String mineZoneId) {
        this.mineZoneId = mineZoneId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("measurementTypeTimestamp")
    @DynamoDbSecondarySortKey(indexNames = "MineTypeIndex")
    public String getMeasurementTypeTimestamp() {
        return measurementTypeTimestamp;
    }

    public void setMeasurementTypeTimestamp(String measurementTypeTimestamp) {
        this.measurementTypeTimestamp = measurementTypeTimestamp;
    }

    @DynamoDbAttribute("mineId")
    @DynamoDbSecondaryPartitionKey(indexNames = "MineTypeIndex")
    public String getMineId() {
        return mineId;
    }

    public void setMineId(String mineId) {
        this.mineId = mineId;
    }

    @DynamoDbAttribute("zoneId")
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @DynamoDbAttribute("measurementType")
    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    @DynamoDbAttribute("alertTimestamp")
    public Long getAlertTimestamp() {
        return alertTimestamp;
    }

    public void setAlertTimestamp(Long alertTimestamp) {
        this.alertTimestamp = alertTimestamp;
    }

    @DynamoDbAttribute("measureTimestamp")
    public Long getMeasureTimestamp() {
        return measureTimestamp;
    }

    public void setMeasureTimestamp(Long measureTimestamp) {
        this.measureTimestamp = measureTimestamp;
    }

    @DynamoDbAttribute("measurementInfo")
    public Map<String, String> getMeasurementInfo() {
        return measurementInfo;
    }

    public void setMeasurementInfo(Map<String, String> measurementInfo) {
        this.measurementInfo = measurementInfo;
    }

    @DynamoDbAttribute("alertInfo")
    public List<AlertInfoEntity> getAlertInfo() {
        return alertInfo;
    }

    public void setAlertInfo(List<AlertInfoEntity> alertInfo) {
        this.alertInfo = alertInfo;
    }
}
