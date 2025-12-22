package com.upiiz.ligas_api_backend.dto.jugador;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JugadorRequest {

    @NotBlank
    private String nombre;

    private String posicion;

    @Min(0)
    private Integer numero;

    @Min(0)
    private Integer edad;

    @NotNull
    private Long equipoId;
}
