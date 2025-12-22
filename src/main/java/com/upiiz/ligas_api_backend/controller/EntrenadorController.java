package com.upiiz.ligas_api_backend.controller;

import com.upiiz.ligas_api_backend.dto.entrenador.EntrenadorRequest;
import com.upiiz.ligas_api_backend.dto.entrenador.EntrenadorResponse;
import com.upiiz.ligas_api_backend.service.EntrenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
@Tag(name = "Entrenadores", description = "CRUD de entrenadores. Hecho por Antonio Valdés Hernández.")
public class EntrenadorController {

    private final EntrenadorService svc;

    public EntrenadorController(EntrenadorService svc) {
        this.svc = svc;
    }

    @Operation(summary = "Listar entrenadores (JWT requerido)")
    @GetMapping
    public ResponseEntity<List<EntrenadorResponse>> all() {
        return ResponseEntity.ok(svc.findAll());
    }

    @Operation(summary = "Obtener entrenador por ID (JWT requerido)")
    @GetMapping("/{id}")
    public ResponseEntity<EntrenadorResponse> one(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }

    @Operation(summary = "Crear entrenador (JWT requerido)")
    @PostMapping
    public ResponseEntity<EntrenadorResponse> create(@Valid @RequestBody EntrenadorRequest req) {
        return ResponseEntity.ok(svc.create(req));
    }

    @Operation(summary = "Actualizar entrenador (JWT requerido)")
    @PutMapping("/{id}")
    public ResponseEntity<EntrenadorResponse> update(@PathVariable Long id, @Valid @RequestBody EntrenadorRequest req) {
        return ResponseEntity.ok(svc.update(id, req));
    }

    @Operation(summary = "Eliminar entrenador (JWT requerido)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
