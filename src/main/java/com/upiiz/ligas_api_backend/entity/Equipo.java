package com.upiiz.ligas_api_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    // Ej: "Águilas", "Panteras"
    private String apodo;

    // Liga a la que pertenece
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "liga_id", nullable = false)
    private Liga liga;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenador_id", unique = true)
    private Entrenador entrenador;

}
