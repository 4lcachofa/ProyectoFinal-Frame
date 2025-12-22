package com.upiiz.ligas_api_backend.service;

import com.upiiz.ligas_api_backend.dto.auth.*;
import com.upiiz.ligas_api_backend.entity.Role;
import com.upiiz.ligas_api_backend.entity.Usuario;
import com.upiiz.ligas_api_backend.repository.UsuarioRepository;
import com.upiiz.ligas_api_backend.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final UserDetailsService uds;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository repo, PasswordEncoder encoder,
                       AuthenticationManager authManager, UserDetailsService uds, JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.uds = uds;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario u = new Usuario();
        u.setEmail(req.getEmail());
        u.setNombre(req.getNombre());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRoles(Set.of(Role.USER)); // por defecto
        repo.save(u);
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        UserDetails user = uds.loadUserByUsername(req.getEmail());
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
