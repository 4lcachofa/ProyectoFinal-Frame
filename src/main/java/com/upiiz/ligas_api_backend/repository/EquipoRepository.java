package com.upiiz.ligas_api_backend.repository;

import com.upiiz.ligas_api_backend.dto.stats.JugadoresPorEquipoResponse;
import com.upiiz.ligas_api_backend.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByLigaId(Long ligaId);
    boolean existsByNombreIgnoreCaseAndLigaId(String nombre, Long ligaId);
    @Query("""
        SELECT new com.upiiz.ligas_api_backend.dto.stats.JugadoresPorEquipoResponse(
            e.id, e.nombre, l.id, l.nombre, COUNT(j.id)
        )
        FROM Equipo e
        JOIN e.liga l
        LEFT JOIN Jugador j ON j.equipo.id = e.id
        GROUP BY e.id, e.nombre, l.id, l.nombre
        ORDER BY COUNT(j.id) DESC
    """)
    List<JugadoresPorEquipoResponse> countJugadoresPorEquipo();
}
