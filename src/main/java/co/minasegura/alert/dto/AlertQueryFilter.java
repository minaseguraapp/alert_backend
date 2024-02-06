package co.minasegura.alert.dto;

public enum AlertQueryFilter {
    MINE("mine"),
    ZONE_ID("zoneId"),
    MEASUREMENT_TYPE("measurementType");

    private final String filter;

    AlertQueryFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return this.filter;
    }
}
