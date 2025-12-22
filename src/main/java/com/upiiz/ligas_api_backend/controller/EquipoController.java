package com.upiiz.ligas_api_backend.controller;

import com.upiiz.ligas_api_backend.dto.equipo.EquipoRequest;
import com.upiiz.ligas_api_backend.dto.equipo.EquipoResponse;
import com.upiiz.ligas_api_backend.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upiiz.ligas_api_backend.dto.jugador.JugadorResponse;
import com.upiiz.ligas_api_backend.service.JugadorService;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@Tag(name = "Equipos", description = "CRUD de equipos. Hecho por Antonio Valdés Hernández.")
public class EquipoController {

    private final EquipoService svc;
    private final JugadorService jugadorSvc;

    public EquipoController(EquipoService svc, JugadorService jugadorSvc) {
        this.svc = svc;
        this.jugadorSvc = jugadorSvc;
    }

    @Operation(summary = "Asignar entrenador a equipo (JWT requerido)",
            description = "Asigna un entrenador a un equipo. Requiere Bearer token.")
    @PutMapping("/{equipoId}/entrenador/{entrenadorId}")
    public ResponseEntity<EquipoResponse> assignEntrenador(
            @PathVariable Long equipoId,
            @PathVariable Long entrenadorId
    ) {
        return ResponseEntity.ok(svc.assignEntrenador(equipoId, entrenadorId));
    }

    @Operation(summary = "Quitar entrenador de equipo (JWT requerido)",
            description = "Elimina la relación entrenador-equipo. Requiere Bearer token.")
    @DeleteMapping("/{equipoId}/entrenador")
    public ResponseEntity<EquipoResponse> removeEntrenador(@PathVariable Long equipoId) {
        return ResponseEntity.ok(svc.removeEntrenador(equipoId));
    }

    @Operation(summary = "Listar jugadores por equipo (JWT requerido)",
            description = "Devuelve los jugadores que pertenecen a un equipo específico.")
    @GetMapping("/{id}/jugadores")
    public ResponseEntity<List<JugadorResponse>> jugadoresPorEquipo(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorSvc.findByEquipo(id));
    }

    @Operation(summary = "Listar equipos (JWT requerido)",
            description = "Devuelve todos los equipos. Requiere Bearer token.")
    @GetMapping
    public ResponseEntity<List<EquipoResponse>> all() {
        return ResponseEntity.ok(svc.findAll());
    }

    @Operation(summary = "Obtener equipo por ID (JWT requerido)",
            description = "Devuelve un equipo por ID. Requiere Bearer token.")
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponse> one(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }

    @Operation(summary = "Crear equipo (JWT requerido)",
            description = "Crea un equipo asignándolo a una liga usando ligaId.")
    @PostMapping
    public ResponseEntity<EquipoResponse> create(@Valid @RequestBody EquipoRequest req) {
        return ResponseEntity.ok(svc.create(req));
    }

    @Operation(summary = "Actualizar equipo (JWT requerido)",
            description = "Actualiza un equipo por ID. Permite cambiar nombre, apodo y ligaId.")
    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponse> update(@PathVariable Long id, @Valid @RequestBody EquipoRequest req) {
        return ResponseEntity.ok(svc.update(id, req));
    }

    @Operation(summary = "Eliminar equipo (JWT requerido)",
            description = "Elimina un equipo por ID. Requiere Bearer token.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
