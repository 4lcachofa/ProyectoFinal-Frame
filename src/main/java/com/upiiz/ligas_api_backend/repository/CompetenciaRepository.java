package com.upiiz.ligas_api_backend.repository;

import com.upiiz.ligas_api_backend.entity.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    List<Competencia> findByEquipoLocalIdOrEquipoVisitaId(Long localId, Long visitaId);
    List<Competencia> findByEquipoLocalLigaId(Long ligaId); // opcional: partidos por liga del local
    @Query(value = """
        SELECT
            e.id AS equipo_id,
            e.nombre AS equipo_nombre,
            l.id AS liga_id,
            l.nombre AS liga_nombre,
            COALESCE(SUM(w.ganado), 0) AS ganados
        FROM equipos e
        JOIN ligas l ON l.id = e.liga_id
        LEFT JOIN (
            SELECT equipo_local_id AS equipo_id, 1 AS ganado
            FROM competencias
            WHERE finalizado = true
              AND goles_local IS NOT NULL AND goles_visita IS NOT NULL
              AND goles_local > goles_visita

            UNION ALL

            SELECT equipo_visita_id AS equipo_id, 1 AS ganado
            FROM competencias
            WHERE finalizado = true
              AND goles_local IS NOT NULL AND goles_visita IS NOT NULL
              AND goles_visita > goles_local
        ) w ON w.equipo_id = e.id
        GROUP BY e.id, e.nombre, l.id, l.nombre
        ORDER BY ganados DESC, e.nombre ASC
        """, nativeQuery = true)
    List<Object[]> equiposMasGanadosRaw();
}
