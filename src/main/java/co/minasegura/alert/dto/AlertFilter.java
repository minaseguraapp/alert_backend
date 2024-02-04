package co.minasegura.alert.dto;

public enum AlertFilter {
    MINE("mine"),
    MEASUREMENT_TYPE("measurementType");
    private final String filter;
    AlertFilter(String filter){this.filter=filter;}

    public String getFilter(){
        return  this.filter;
    }
}
