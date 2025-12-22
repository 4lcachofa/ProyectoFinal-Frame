package com.upiiz.ligas_api_backend.dto.entrenador;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EntrenadorRequest {

    @NotBlank
    private String nombre;

    @Min(0)
    private Integer experienciaAnios;

    private String especialidad;
}
