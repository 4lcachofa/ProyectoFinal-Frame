package com.upiiz.ligas_api_backend.dto.equipo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipoResponse {
    private Long id;
    private String nombre;
    private String apodo;
    private Long ligaId;
    private String ligaNombre;
    private Long entrenadorId;
    private String entrenadorNombre;

}
