package com.upiiz.ligas_api_backend.dto.jugador;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JugadorResponse {
    private Long id;
    private String nombre;
    private String posicion;
    private Integer numero;
    private Integer edad;

    private Long equipoId;
    private String equipoNombre;

    private Long ligaId;
    private String ligaNombre;
}
