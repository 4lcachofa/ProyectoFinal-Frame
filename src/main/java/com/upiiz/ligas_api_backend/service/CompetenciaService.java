package com.upiiz.ligas_api_backend.service;

import com.upiiz.ligas_api_backend.dto.competencia.*;
import com.upiiz.ligas_api_backend.entity.Competencia;
import com.upiiz.ligas_api_backend.repository.CompetenciaRepository;
import com.upiiz.ligas_api_backend.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenciaService {

    private final CompetenciaRepository compRepo;
    private final EquipoRepository equipoRepo;

    public CompetenciaService(CompetenciaRepository compRepo, EquipoRepository equipoRepo) {
        this.compRepo = compRepo;
        this.equipoRepo = equipoRepo;
    }

    public List<CompetenciaResponse> findAll() {
        return compRepo.findAll().stream().map(this::toResponse).toList();
    }

    public CompetenciaResponse findById(Long id) {
        var c = compRepo.findById(id).orElseThrow(() -> new RuntimeException("Competencia no encontrada"));
        return toResponse(c);
    }

    public CompetenciaResponse create(CompetenciaCreateRequest req) {
        if (req.getEquipoLocalId().equals(req.getEquipoVisitaId())) {
            throw new RuntimeException("El equipo local y visita no pueden ser el mismo");
        }

        var local = equipoRepo.findById(req.getEquipoLocalId())
                .orElseThrow(() -> new RuntimeException("Equipo local no encontrado"));

        var visita = equipoRepo.findById(req.getEquipoVisitaId())
                .orElseThrow(() -> new RuntimeException("Equipo visita no encontrado"));

        // Regla útil: ambos equipos deben pertenecer a la misma liga
        if (!local.getLiga().getId().equals(visita.getLiga().getId())) {
            throw new RuntimeException("Los equipos deben pertenecer a la misma liga");
        }

        Competencia c = new Competencia();
        c.setFecha(req.getFecha());
        c.setLugar(req.getLugar());
        c.setEquipoLocal(local);
        c.setEquipoVisita(visita);
        c.setGolesLocal(null);
        c.setGolesVisita(null);
        c.setFinalizado(false);

        return toResponse(compRepo.save(c));
    }

    public CompetenciaResponse updateAgenda(Long id, CompetenciaCreateRequest req) {
        var c = compRepo.findById(id).orElseThrow(() -> new RuntimeException("Competencia no encontrada"));

        if (req.getEquipoLocalId().equals(req.getEquipoVisitaId())) {
            throw new RuntimeException("El equipo local y visita no pueden ser el mismo");
        }

        var local = equipoRepo.findById(req.getEquipoLocalId())
                .orElseThrow(() -> new RuntimeException("Equipo local no encontrado"));

        var visita = equipoRepo.findById(req.getEquipoVisitaId())
                .orElseThrow(() -> new RuntimeException("Equipo visita no encontrado"));

        if (!local.getLiga().getId().equals(visita.getLiga().getId())) {
            throw new RuntimeException("Los equipos deben pertenecer a la misma liga");
        }

        c.setFecha(req.getFecha());
        c.setLugar(req.getLugar());
        c.setEquipoLocal(local);
        c.setEquipoVisita(visita);

        return toResponse(compRepo.save(c));
    }

    public CompetenciaResponse setResultado(Long id, CompetenciaResultadoRequest req) {
        var c = compRepo.findById(id).orElseThrow(() -> new RuntimeException("Competencia no encontrada"));

        c.setGolesLocal(req.getGolesLocal());
        c.setGolesVisita(req.getGolesVisita());

        boolean fin = req.getFinalizado() != null ? req.getFinalizado() : true;
        c.setFinalizado(fin);

        return toResponse(compRepo.save(c));
    }

    public void delete(Long id) {
        if (!compRepo.existsById(id)) throw new RuntimeException("Competencia no encontrada");
        compRepo.deleteById(id);
    }

    private CompetenciaResponse toResponse(Competencia c) {
        return new CompetenciaResponse(
                c.getId(),
                c.getFecha(),
                c.getLugar(),
                c.getEquipoLocal().getId(),
                c.getEquipoLocal().getNombre(),
                c.getEquipoVisita().getId(),
                c.getEquipoVisita().getNombre(),
                c.getGolesLocal(),
                c.getGolesVisita(),
                c.isFinalizado()
        );
    }
}
