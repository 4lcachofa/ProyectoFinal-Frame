package com.upiiz.ligas_api_backend.dto.equipo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipoRequest {

    @NotBlank
    private String nombre;

    private String apodo;

    @NotNull
    private Long ligaId;
}
