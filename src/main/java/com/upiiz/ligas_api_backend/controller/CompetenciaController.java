package com.upiiz.ligas_api_backend.controller;

import com.upiiz.ligas_api_backend.dto.competencia.*;
import com.upiiz.ligas_api_backend.service.CompetenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competencias")
@Tag(name = "Competencias", description = "Partidos/competencias. Hecho por Antonio Valdés Hernández.")
public class CompetenciaController {

    private final CompetenciaService svc;

    public CompetenciaController(CompetenciaService svc) {
        this.svc = svc;
    }

    @Operation(summary = "Listar competencias (JWT requerido)")
    @GetMapping
    public ResponseEntity<List<CompetenciaResponse>> all() {
        return ResponseEntity.ok(svc.findAll());
    }

    @Operation(summary = "Obtener competencia por ID (JWT requerido)")
    @GetMapping("/{id}")
    public ResponseEntity<CompetenciaResponse> one(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }

    @Operation(summary = "Crear competencia (JWT requerido)",
            description = "Crea un partido: fecha, lugar, equipoLocalId, equipoVisitaId. Ambos equipos deben ser de la misma liga.")
    @PostMapping
    public ResponseEntity<CompetenciaResponse> create(@Valid @RequestBody CompetenciaCreateRequest req) {
        return ResponseEntity.ok(svc.create(req));
    }

    @Operation(summary = "Actualizar agenda de competencia (JWT requerido)",
            description = "Actualiza fecha/lugar/equipos del partido. No cambia el resultado.")
    @PutMapping("/{id}")
    public ResponseEntity<CompetenciaResponse> updateAgenda(@PathVariable Long id, @Valid @RequestBody CompetenciaCreateRequest req) {
        return ResponseEntity.ok(svc.updateAgenda(id, req));
    }

    @Operation(summary = "Registrar/actualizar resultado (JWT requerido)",
            description = "Captura golesLocal/golesVisita y marca finalizado=true por defecto.")
    @PutMapping("/{id}/resultado")
    public ResponseEntity<CompetenciaResponse> setResultado(@PathVariable Long id, @Valid @RequestBody CompetenciaResultadoRequest req) {
        return ResponseEntity.ok(svc.setResultado(id, req));
    }

    @Operation(summary = "Eliminar competencia (JWT requerido)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
