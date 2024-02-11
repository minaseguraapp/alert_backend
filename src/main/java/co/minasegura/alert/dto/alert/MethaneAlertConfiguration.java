package co.minasegura.alert.dto.alert;

public record MethaneAlertConfiguration(String miningOperationsMaxMethaneLevel,
                                        String mainAirReturnMaxMethaneLevel,
                                        String airReturnFromStallsMaxMethaneLevel,
                                        String airReturnFromPrepAndDevMaxMethaneLevel) {
}
