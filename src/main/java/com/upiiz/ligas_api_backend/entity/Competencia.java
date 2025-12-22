package com.upiiz.ligas_api_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "competencias")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Partido programado
    @Column(nullable = false)
    private LocalDateTime fecha;

    private String lugar;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipo_local_id", nullable = false)
    private Equipo equipoLocal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipo_visita_id", nullable = false)
    private Equipo equipoVisita;

    // Resultado (puede estar null hasta capturar)
    @Min(0)
    private Integer golesLocal;

    @Min(0)
    private Integer golesVisita;

    // opcional: estado
    @Column(nullable = false)
    private boolean finalizado = false;
}
