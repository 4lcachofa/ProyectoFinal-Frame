package com.upiiz.ligas_api_backend.service;

import com.upiiz.ligas_api_backend.dto.entrenador.EntrenadorRequest;
import com.upiiz.ligas_api_backend.dto.entrenador.EntrenadorResponse;
import com.upiiz.ligas_api_backend.entity.Entrenador;
import com.upiiz.ligas_api_backend.repository.EntrenadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorService {

    private final EntrenadorRepository repo;

    public EntrenadorService(EntrenadorRepository repo) {
        this.repo = repo;
    }

    public List<EntrenadorResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public EntrenadorResponse findById(Long id) {
        var e = repo.findById(id).orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        return toResponse(e);
    }

    public EntrenadorResponse create(EntrenadorRequest req) {
        Entrenador e = new Entrenador();
        e.setNombre(req.getNombre());
        e.setExperienciaAnios(req.getExperienciaAnios());
        e.setEspecialidad(req.getEspecialidad());
        return toResponse(repo.save(e));
    }

    public EntrenadorResponse update(Long id, EntrenadorRequest req) {
        Entrenador current = repo.findById(id).orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        current.setNombre(req.getNombre());
        current.setExperienciaAnios(req.getExperienciaAnios());
        current.setEspecialidad(req.getEspecialidad());
        return toResponse(repo.save(current));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Entrenador no encontrado");
        repo.deleteById(id);
    }

    private EntrenadorResponse toResponse(Entrenador e) {
        return new EntrenadorResponse(e.getId(), e.getNombre(), e.getExperienciaAnios(), e.getEspecialidad());
    }
}
