package com.upiiz.ligas_api_backend.repository;

import com.upiiz.ligas_api_backend.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    List<Jugador> findByEquipoId(Long equipoId);
    boolean existsByNombreIgnoreCaseAndEquipoId(String nombre, Long equipoId);
}
