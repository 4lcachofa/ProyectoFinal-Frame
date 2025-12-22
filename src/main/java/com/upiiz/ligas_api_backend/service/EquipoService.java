package com.upiiz.ligas_api_backend.service;

import com.upiiz.ligas_api_backend.dto.equipo.EquipoRequest;
import com.upiiz.ligas_api_backend.dto.equipo.EquipoResponse;
import com.upiiz.ligas_api_backend.entity.Equipo;
import com.upiiz.ligas_api_backend.entity.Liga;
import com.upiiz.ligas_api_backend.repository.EquipoRepository;
import com.upiiz.ligas_api_backend.repository.LigaRepository;
import org.springframework.stereotype.Service;
import com.upiiz.ligas_api_backend.entity.Entrenador;
import com.upiiz.ligas_api_backend.repository.EntrenadorRepository;


import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepo;
    private final LigaRepository ligaRepo;
    private final EntrenadorRepository entrenadorRepo;

    public EquipoService(EquipoRepository equipoRepo, LigaRepository ligaRepo, EntrenadorRepository entrenadorRepo) {
        this.equipoRepo = equipoRepo;
        this.ligaRepo = ligaRepo;
        this.entrenadorRepo = entrenadorRepo;
    }

    public EquipoResponse assignEntrenador(Long equipoId, Long entrenadorId) {
        var equipo = equipoRepo.findById(equipoId).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        var entrenador = entrenadorRepo.findById(entrenadorId).orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        // Si quieres forzar que un entrenador no esté en otro equipo, ya lo cubre unique=true.
        equipo.setEntrenador(entrenador);
        return toResponse(equipoRepo.save(equipo));
    }

    public EquipoResponse removeEntrenador(Long equipoId) {
        var equipo = equipoRepo.findById(equipoId).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        equipo.setEntrenador(null);
        return toResponse(equipoRepo.save(equipo));
    }

    public List<EquipoResponse> findAll() {
        return equipoRepo.findAll().stream().map(this::toResponse).toList();
    }

    public EquipoResponse findById(Long id) {
        Equipo e = equipoRepo.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return toResponse(e);
    }

    public List<EquipoResponse> findByLiga(Long ligaId) {
        return equipoRepo.findByLigaId(ligaId).stream().map(this::toResponse).toList();
    }

    public EquipoResponse create(EquipoRequest req) {
        Liga liga = ligaRepo.findById(req.getLigaId())
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        if (equipoRepo.existsByNombreIgnoreCaseAndLigaId(req.getNombre(), liga.getId())) {
            throw new RuntimeException("Ya existe un equipo con ese nombre en esa liga");
        }

        Equipo e = new Equipo();
        e.setNombre(req.getNombre());
        e.setApodo(req.getApodo());
        e.setLiga(liga);

        return toResponse(equipoRepo.save(e));
    }

    public EquipoResponse update(Long id, EquipoRequest req) {
        Equipo current = equipoRepo.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        Liga liga = ligaRepo.findById(req.getLigaId())
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        // Si cambian nombre o liga, validamos duplicado por liga
        boolean cambioLiga = !current.getLiga().getId().equals(liga.getId());
        boolean cambioNombre = !current.getNombre().equalsIgnoreCase(req.getNombre());

        if (cambioLiga || cambioNombre) {
            if (equipoRepo.existsByNombreIgnoreCaseAndLigaId(req.getNombre(), liga.getId())) {
                throw new RuntimeException("Ya existe un equipo con ese nombre en esa liga");
            }
        }

        current.setNombre(req.getNombre());
        current.setApodo(req.getApodo());
        current.setLiga(liga);

        return toResponse(equipoRepo.save(current));
    }

    public void delete(Long id) {
        if (!equipoRepo.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado");
        }
        equipoRepo.deleteById(id);
    }

    private EquipoResponse toResponse(Equipo e) {
        Long entrenadorId = (e.getEntrenador() != null) ? e.getEntrenador().getId() : null;
        String entrenadorNombre = (e.getEntrenador() != null) ? e.getEntrenador().getNombre() : null;

        return new EquipoResponse(
                e.getId(),
                e.getNombre(),
                e.getApodo(),
                e.getLiga().getId(),
                e.getLiga().getNombre(),
                entrenadorId,
                entrenadorNombre
        );
    }

}
