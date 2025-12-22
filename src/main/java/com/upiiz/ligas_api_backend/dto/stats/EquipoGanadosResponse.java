package com.upiiz.ligas_api_backend.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipoGanadosResponse {
    private Long equipoId;
    private String equipoNombre;
    private Long ligaId;
    private String ligaNombre;
    private Long ganados;
}
