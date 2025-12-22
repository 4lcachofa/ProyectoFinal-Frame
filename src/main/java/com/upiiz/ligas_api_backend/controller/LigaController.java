package com.upiiz.ligas_api_backend.controller;

import com.upiiz.ligas_api_backend.entity.Liga;
import com.upiiz.ligas_api_backend.service.LigaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upiiz.ligas_api_backend.dto.equipo.EquipoResponse;
import com.upiiz.ligas_api_backend.service.EquipoService;

import java.util.List;

@RestController
@RequestMapping("/api/ligas")
@Tag(name = "Ligas", description = "CRUD de ligas deportivas. Hecho por Antonio Valdés Hernández.")
public class LigaController {

    private final LigaService svc;
    private final EquipoService equipoSvc;

    public LigaController(LigaService svc, EquipoService equipoSvc) {
        this.svc = svc;
        this.equipoSvc = equipoSvc;
    }
    @Operation(summary = "Listar equipos por liga (JWT requerido)",
            description = "Devuelve los equipos que pertenecen a una liga específica.")
    @GetMapping("/{id}/equipos")
    public ResponseEntity<List<EquipoResponse>> equiposPorLiga(@PathVariable Long id) {
        return ResponseEntity.ok(equipoSvc.findByLiga(id));
    }

    @Operation(summary = "Listar ligas (público)",
            description = "Devuelve todas las ligas. Este endpoint es público (no requiere JWT).")
    @GetMapping
    public ResponseEntity<List<Liga>> all() {
        return ResponseEntity.ok(svc.findAll());
    }

    @Operation(summary = "Obtener liga por ID (público)",
            description = "Devuelve una liga por ID. Este endpoint es público (no requiere JWT).")
    @GetMapping("/{id}")
    public ResponseEntity<Liga> one(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }

    @Operation(summary = "Crear liga (JWT requerido)",
            description = "Crea una liga. Requiere Authorization: Bearer <token>.")
    @PostMapping
    public ResponseEntity<Liga> create(@Valid @RequestBody Liga liga) {
        return ResponseEntity.ok(svc.create(liga));
    }

    @Operation(summary = "Actualizar liga (JWT requerido)",
            description = "Actualiza una liga por ID. Requiere Authorization: Bearer <token>.")
    @PutMapping("/{id}")
    public ResponseEntity<Liga> update(@PathVariable Long id, @Valid @RequestBody Liga liga) {
        return ResponseEntity.ok(svc.update(id, liga));
    }

    @Operation(summary = "Eliminar liga (solo ADMIN)",
            description = "Elimina una liga por ID. Requiere rol ADMIN (ROLE_ADMIN).")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
