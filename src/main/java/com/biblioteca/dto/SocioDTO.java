package com.biblioteca.dto;

import com.biblioteca.model.Socio;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

public class SocioDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        private String nombre;

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100)
        private String apellido;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email no válido")
        private String email;

        @Size(max = 20)
        private String telefono;

        @Size(max = 255)
        private String direccion;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
        private String direccion;
        private LocalDate fechaRegistro;
        private Boolean activo;
        private Integer prestamosActivos;

        public static Response from(Socio socio) {
            long activos = socio.getPrestamos() != null
                    ? socio.getPrestamos().stream()
                        .filter(p -> p.getEstado() == com.biblioteca.model.Prestamo.EstadoPrestamo.ACTIVO)
                        .count()
                    : 0;
            return Response.builder()
                    .id(socio.getId())
                    .nombre(socio.getNombre())
                    .apellido(socio.getApellido())
                    .email(socio.getEmail())
                    .telefono(socio.getTelefono())
                    .direccion(socio.getDireccion())
                    .fechaRegistro(socio.getFechaRegistro())
                    .activo(socio.getActivo())
                    .prestamosActivos((int) activos)
                    .build();
        }
    }
}
