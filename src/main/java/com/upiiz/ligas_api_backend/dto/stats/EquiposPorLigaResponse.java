package com.upiiz.ligas_api_backend.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquiposPorLigaResponse {
    private Long ligaId;
    private String ligaNombre;
    private Long totalEquipos;
}
