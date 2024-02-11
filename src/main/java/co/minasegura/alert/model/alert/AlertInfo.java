package co.minasegura.alert.model.alert;

public record AlertInfo(
    String thresholdName,
    String valueExpected,
    String valueFound
) {}