package com.upiiz.ligas_api_backend.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
public class ApiError {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    // Para errores de validación: campo -> mensaje
    private Map<String, String> fieldErrors;
}
