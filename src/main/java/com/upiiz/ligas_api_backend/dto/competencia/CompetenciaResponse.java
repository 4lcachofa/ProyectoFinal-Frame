package com.upiiz.ligas_api_backend.dto.competencia;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CompetenciaResponse {
    private Long id;
    private LocalDateTime fecha;
    private String lugar;

    private Long equipoLocalId;
    private String equipoLocalNombre;

    private Long equipoVisitaId;
    private String equipoVisitaNombre;

    private Integer golesLocal;
    private Integer golesVisita;

    private boolean finalizado;
}
