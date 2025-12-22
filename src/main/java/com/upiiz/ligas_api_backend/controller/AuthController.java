package com.upiiz.ligas_api_backend.controller;

import com.upiiz.ligas_api_backend.dto.auth.*;
import com.upiiz.ligas_api_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Registro y login con JWT. Hecho por Antonio Valdés Hernández.")
public class AuthController {

    private final AuthService svc;

    public AuthController(AuthService svc) {
        this.svc = svc;
    }

    @Operation(summary = "Registrar usuario", description = "Crea un usuario con rol USER por defecto.")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest req) {
        svc.register(req);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Login", description = "Autentica y devuelve un JWT (Bearer) para endpoints protegidos.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(svc.login(req));
    }
}
