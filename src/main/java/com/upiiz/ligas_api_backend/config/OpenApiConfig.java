package com.upiiz.ligas_api_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String schemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Proyecto Final — Gestión de Ligas Deportivas")
                        .version("1.0.0")
                        .description("""
                                API segura con Spring Boot + JWT para gestionar ligas, equipos, jugadores, entrenadores y competencias.

                                **Hecho por Antonio Valdés Hernández**
                                """)
                        .contact(new Contact()
                                .name("Antonio Valdés Hernández")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(schemeName,
                                new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}