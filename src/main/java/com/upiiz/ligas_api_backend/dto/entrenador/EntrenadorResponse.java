package com.upiiz.ligas_api_backend.dto.entrenador;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntrenadorResponse {
    private Long id;
    private String nombre;
    private Integer experienciaAnios;
    private String especialidad;
}
