package com.upiiz.ligas_api_backend.service;

import com.upiiz.ligas_api_backend.dto.jugador.JugadorRequest;
import com.upiiz.ligas_api_backend.dto.jugador.JugadorResponse;
import com.upiiz.ligas_api_backend.entity.Equipo;
import com.upiiz.ligas_api_backend.entity.Jugador;
import com.upiiz.ligas_api_backend.repository.EquipoRepository;
import com.upiiz.ligas_api_backend.repository.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepo;
    private final EquipoRepository equipoRepo;

    public JugadorService(JugadorRepository jugadorRepo, EquipoRepository equipoRepo) {
        this.jugadorRepo = jugadorRepo;
        this.equipoRepo = equipoRepo;
    }

    public List<JugadorResponse> findAll() {
        return jugadorRepo.findAll().stream().map(this::toResponse).toList();
    }

    public JugadorResponse findById(Long id) {
        Jugador j = jugadorRepo.findById(id).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        return toResponse(j);
    }

    public List<JugadorResponse> findByEquipo(Long equipoId) {
        return jugadorRepo.findByEquipoId(equipoId).stream().map(this::toResponse).toList();
    }

    public JugadorResponse create(JugadorRequest req) {
        Equipo equipo = equipoRepo.findById(req.getEquipoId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        if (jugadorRepo.existsByNombreIgnoreCaseAndEquipoId(req.getNombre(), equipo.getId())) {
            throw new RuntimeException("Ya existe un jugador con ese nombre en ese equipo");
        }

        Jugador j = new Jugador();
        j.setNombre(req.getNombre());
        j.setPosicion(req.getPosicion());
        j.setNumero(req.getNumero());
        j.setEdad(req.getEdad());
        j.setEquipo(equipo);

        return toResponse(jugadorRepo.save(j));
    }

    public JugadorResponse update(Long id, JugadorRequest req) {
        Jugador current = jugadorRepo.findById(id).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        Equipo equipo = equipoRepo.findById(req.getEquipoId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        boolean cambioEquipo = !current.getEquipo().getId().equals(equipo.getId());
        boolean cambioNombre = !current.getNombre().equalsIgnoreCase(req.getNombre());

        if (cambioEquipo || cambioNombre) {
            if (jugadorRepo.existsByNombreIgnoreCaseAndEquipoId(req.getNombre(), equipo.getId())) {
                throw new RuntimeException("Ya existe un jugador con ese nombre en ese equipo");
            }
        }

        current.setNombre(req.getNombre());
        current.setPosicion(req.getPosicion());
        current.setNumero(req.getNumero());
        current.setEdad(req.getEdad());
        current.setEquipo(equipo);

        return toResponse(jugadorRepo.save(current));
    }

    public void delete(Long id) {
        if (!jugadorRepo.existsById(id)) {
            throw new RuntimeException("Jugador no encontrado");
        }
        jugadorRepo.deleteById(id);
    }

    private JugadorResponse toResponse(Jugador j) {
        var equipo = j.getEquipo();
        var liga = equipo.getLiga();

        return new JugadorResponse(
                j.getId(),
                j.getNombre(),
                j.getPosicion(),
                j.getNumero(),
                j.getEdad(),
                equipo.getId(),
                equipo.getNombre(),
                liga.getId(),
                liga.getNombre()
        );
    }
}
