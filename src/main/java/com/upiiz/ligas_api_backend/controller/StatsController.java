package com.upiiz.ligas_api_backend.controller;

import com.upiiz.ligas_api_backend.dto.stats.EquipoGanadosResponse;
import com.upiiz.ligas_api_backend.dto.stats.EquiposPorLigaResponse;
import com.upiiz.ligas_api_backend.dto.stats.JugadoresPorEquipoResponse;
import com.upiiz.ligas_api_backend.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
@Tag(name = "Estadísticas", description = "Endpoints para gráficos. Hecho por Antonio Valdés Hernández.")
public class StatsController {

    private final StatsService svc;

    public StatsController(StatsService svc) {
        this.svc = svc;
    }

    @Operation(summary = "Equipos con más partidos ganados (JWT requerido)",
            description = "Cuenta victorias en competencias finalizadas (sin empates).")
    @GetMapping("/equipos-mas-ganados")
    public ResponseEntity<List<EquipoGanadosResponse>> equiposMasGanados() {
        return ResponseEntity.ok(svc.equiposMasGanados());
    }

    @Operation(summary = "Número de equipos por liga (JWT requerido)",
            description = "Devuelve el total de equipos agrupado por liga.")
    @GetMapping("/equipos-por-liga")
    public ResponseEntity<List<EquiposPorLigaResponse>> equiposPorLiga() {
        return ResponseEntity.ok(svc.equiposPorLiga());
    }

    @Operation(summary = "Total de jugadores por equipo (JWT requerido)",
            description = "Devuelve cuántos jugadores tiene cada equipo (útil para gráficos).")
    @GetMapping("/jugadores-por-equipo")
    public ResponseEntity<List<JugadoresPorEquipoResponse>> jugadoresPorEquipo() {
        return ResponseEntity.ok(svc.jugadoresPorEquipo());
    }

    @Operation(summary = "Promedio global de jugadores por equipo (JWT requerido)",
            description = "Calcula el promedio global (total jugadores / total equipos).")
    @GetMapping("/promedio-jugadores-por-equipo")
    public ResponseEntity<Object> promedioGlobal() {
        var res = new HashMap<String, Object>();
        res.put("promedio", svc.promedioJugadoresPorEquipoGlobal());
        return ResponseEntity.ok(res);
    }
}
