package com.upiiz.ligas_api_backend.service;

import com.upiiz.ligas_api_backend.dto.stats.EquipoGanadosResponse;
import com.upiiz.ligas_api_backend.dto.stats.EquiposPorLigaResponse;
import com.upiiz.ligas_api_backend.dto.stats.JugadoresPorEquipoResponse;
import com.upiiz.ligas_api_backend.repository.CompetenciaRepository;
import com.upiiz.ligas_api_backend.repository.EquipoRepository;
import com.upiiz.ligas_api_backend.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    private final CompetenciaRepository compRepo;
    private final LigaRepository ligaRepo;
    private final EquipoRepository equipoRepo;

    public StatsService(CompetenciaRepository compRepo, LigaRepository ligaRepo, EquipoRepository equipoRepo) {
        this.compRepo = compRepo;
        this.ligaRepo = ligaRepo;
        this.equipoRepo = equipoRepo;
    }

    public List<EquipoGanadosResponse> equiposMasGanados() {
        return compRepo.equiposMasGanadosRaw().stream().map(row -> {
            // Row: equipo_id, equipo_nombre, liga_id, liga_nombre, ganados
            Long equipoId = ((Number) row[0]).longValue();
            String equipoNombre = (String) row[1];
            Long ligaId = ((Number) row[2]).longValue();
            String ligaNombre = (String) row[3];
            Long ganados = ((Number) row[4]).longValue();
            return new EquipoGanadosResponse(equipoId, equipoNombre, ligaId, ligaNombre, ganados);
        }).toList();
    }

    public List<EquiposPorLigaResponse> equiposPorLiga() {
        return ligaRepo.countEquiposPorLiga();
    }

    public List<JugadoresPorEquipoResponse> jugadoresPorEquipo() {
        return equipoRepo.countJugadoresPorEquipo();
    }

    // (Opcional) promedio global de jugadores por equipo
    public double promedioJugadoresPorEquipoGlobal() {
        var lista = equipoRepo.countJugadoresPorEquipo();
        if (lista.isEmpty()) return 0.0;
        long total = lista.stream().mapToLong(JugadoresPorEquipoResponse::getTotalJugadores).sum();
        return (double) total / (double) lista.size();
    }
}
