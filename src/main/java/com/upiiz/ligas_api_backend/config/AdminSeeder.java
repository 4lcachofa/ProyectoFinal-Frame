package com.upiiz.ligas_api_backend.config;

import com.upiiz.ligas_api_backend.entity.Role;
import com.upiiz.ligas_api_backend.entity.Usuario;
import com.upiiz.ligas_api_backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public AdminSeeder(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        String adminEmail = "admin@upiiz.com";
        if (repo.existsByEmail(adminEmail)) return;

        Usuario admin = new Usuario();
        admin.setEmail(adminEmail);
        admin.setNombre("Administrador");
        admin.setPassword(encoder.encode("admin123"));
        admin.setRoles(Set.of(Role.ADMIN, Role.USER));
        repo.save(admin);

        System.out.println("[SEED] Admin creado: admin@upiiz.com / admin123");
    }
}
