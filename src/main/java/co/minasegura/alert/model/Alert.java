package co.minasegura.alert.model;

import java.util.List;
import java.util.Map;

public record Alert(
    String mineId,
    String zoneId,
    String measurementType,
    Long alertTimestamp,
    Long measureTimestamp,
    Map<String, String> measurementInfo,
    List<AlertInfo> alertInfo
){}
