package com.upiiz.ligas_api_backend.dto.competencia;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompetenciaResultadoRequest {

    @NotNull @Min(0)
    private Integer golesLocal;

    @NotNull @Min(0)
    private Integer golesVisita;

    // Por si quieres marcar finalizado al capturar resultado
    private Boolean finalizado = true;
}
