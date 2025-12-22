package com.upiiz.ligas_api_backend.controller;

import com.upiiz.ligas_api_backend.dto.jugador.JugadorRequest;
import com.upiiz.ligas_api_backend.dto.jugador.JugadorResponse;
import com.upiiz.ligas_api_backend.service.JugadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@Tag(name = "Jugadores", description = "CRUD de jugadores. Hecho por Antonio Valdés Hernández.")
public class JugadorController {

    private final JugadorService svc;

    public JugadorController(JugadorService svc) {
        this.svc = svc;
    }

    @Operation(summary = "Listar jugadores (JWT requerido)")
    @GetMapping
    public ResponseEntity<List<JugadorResponse>> all() {
        return ResponseEntity.ok(svc.findAll());
    }

    @Operation(summary = "Obtener jugador por ID (JWT requerido)")
    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponse> one(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }

    @Operation(summary = "Crear jugador (JWT requerido)",
            description = "Crea un jugador y lo asigna a un equipo con equipoId.")
    @PostMapping
    public ResponseEntity<JugadorResponse> create(@Valid @RequestBody JugadorRequest req) {
        return ResponseEntity.ok(svc.create(req));
    }

    @Operation(summary = "Actualizar jugador (JWT requerido)")
    @PutMapping("/{id}")
    public ResponseEntity<JugadorResponse> update(@PathVariable Long id, @Valid @RequestBody JugadorRequest req) {
        return ResponseEntity.ok(svc.update(id, req));
    }

    @Operation(summary = "Eliminar jugador (JWT requerido)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
