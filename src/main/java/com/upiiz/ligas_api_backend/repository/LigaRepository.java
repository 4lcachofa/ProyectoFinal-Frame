package com.upiiz.ligas_api_backend.repository;

import com.upiiz.ligas_api_backend.dto.stats.EquiposPorLigaResponse;
import com.upiiz.ligas_api_backend.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LigaRepository extends JpaRepository<Liga, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    @Query("""
        SELECT new com.upiiz.ligas_api_backend.dto.stats.EquiposPorLigaResponse(
            l.id, l.nombre, COUNT(e.id)
        )
        FROM Liga l
        LEFT JOIN Equipo e ON e.liga.id = l.id
        GROUP BY l.id, l.nombre
        ORDER BY COUNT(e.id) DESC
    """)
    List<EquiposPorLigaResponse> countEquiposPorLiga();
}
