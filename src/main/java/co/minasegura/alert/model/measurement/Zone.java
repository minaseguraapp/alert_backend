package co.minasegura.alert.model.measurement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record Zone(@NotNull String id, @NotNull String type, @NotNull @Valid Mine mine){}
