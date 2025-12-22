package com.upiiz.ligas_api_backend.dto.competencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompetenciaCreateRequest {

    @NotNull
    private LocalDateTime fecha;

    private String lugar;

    @NotNull
    private Long equipoLocalId;

    @NotNull
    private Long equipoVisitaId;
}
