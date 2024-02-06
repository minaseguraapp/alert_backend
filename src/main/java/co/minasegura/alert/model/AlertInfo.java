package co.minasegura.alert.model;

public record AlertInfo(
    String thresholdName,
    String valueExpected,
    String valueFound
) {}