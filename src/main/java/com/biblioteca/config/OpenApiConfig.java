package com.biblioteca.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bibliotecaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("📚 Biblioteca API")
                        .description("""
                                API REST para la gestión completa de una biblioteca.
                                
                                **Funcionalidades:**
                                - 📖 Gestión del catálogo de libros
                                - ✍️ Gestión de autores
                                - 👤 Gestión de socios
                                - 🔄 Control de préstamos y devoluciones
                                
                                **Reglas de negocio:**
                                - Un socio puede tener máximo 3 préstamos activos
                                - El plazo por defecto de devolución es de 15 días
                                - Solo socios activos pueden realizar préstamos
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Biblioteca API")
                                .email("admin@biblioteca.com"))
                        .license(new License()
                                .name("MIT License")));
    }
}
