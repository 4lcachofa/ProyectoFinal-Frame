package com.upiiz.ligas_api_backend.service;

import com.upiiz.ligas_api_backend.entity.Liga;
import com.upiiz.ligas_api_backend.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigaService {

    private final LigaRepository repo;

    public LigaService(LigaRepository repo) {
        this.repo = repo;
    }

    public List<Liga> findAll() {
        return repo.findAll();
    }

    public Liga findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Liga no encontrada"));
    }

    public Liga create(Liga liga) {
        if (repo.existsByNombreIgnoreCase(liga.getNombre())) {
            throw new RuntimeException("Ya existe una liga con ese nombre");
        }
        liga.setId(null);
        return repo.save(liga);
    }

    public Liga update(Long id, Liga liga) {
        Liga current = findById(id);

        // Si cambian el nombre, validamos duplicado
        String nuevoNombre = liga.getNombre();
        if (nuevoNombre != null && !nuevoNombre.equalsIgnoreCase(current.getNombre())) {
            if (repo.existsByNombreIgnoreCase(nuevoNombre)) {
                throw new RuntimeException("Ya existe una liga con ese nombre");
            }
            current.setNombre(nuevoNombre);
        }

        current.setCategoria(liga.getCategoria());
        current.setDescripcion(liga.getDescripcion());
        return repo.save(current);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Liga no encontrada");
        }
        repo.deleteById(id);
    }
}
