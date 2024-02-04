package co.minasegura.alert.dto;

public enum AlertConfigurationFilter {
    MINE("mine"),
    MEASUREMENT_TYPE("measurementType");
    private final String filter;
    AlertConfigurationFilter(String filter){this.filter=filter;}

    public String getFilter(){
        return  this.filter;
    }
}
