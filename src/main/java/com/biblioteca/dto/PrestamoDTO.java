package com.biblioteca.dto;

import com.biblioteca.model.Prestamo;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class PrestamoDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull(message = "El ID del libro es obligatorio")
        private Long libroId;

        @NotNull(message = "El ID del socio es obligatorio")
        private Long socioId;

        @Future(message = "La fecha de devolución debe ser futura")
        private LocalDate fechaDevolucionPrevista;

        private String observaciones;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private Long libroId;
        private String libroTitulo;
        private String libroIsbn;
        private Long socioId;
        private String socioNombreCompleto;
        private String socioEmail;
        private LocalDate fechaPrestamo;
        private LocalDate fechaDevolucionPrevista;
        private LocalDate fechaDevolucionReal;
        private Prestamo.EstadoPrestamo estado;
        private String observaciones;
        private Boolean vencido;

        public static Response from(Prestamo prestamo) {
            return Response.builder()
                    .id(prestamo.getId())
                    .libroId(prestamo.getLibro().getId())
                    .libroTitulo(prestamo.getLibro().getTitulo())
                    .libroIsbn(prestamo.getLibro().getIsbn())
                    .socioId(prestamo.getSocio().getId())
                    .socioNombreCompleto(prestamo.getSocio().getNombre() + " " + prestamo.getSocio().getApellido())
                    .socioEmail(prestamo.getSocio().getEmail())
                    .fechaPrestamo(prestamo.getFechaPrestamo())
                    .fechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista())
                    .fechaDevolucionReal(prestamo.getFechaDevolucionReal())
                    .estado(prestamo.getEstado())
                    .observaciones(prestamo.getObservaciones())
                    .vencido(prestamo.getEstado() == Prestamo.EstadoPrestamo.ACTIVO
                            && LocalDate.now().isAfter(prestamo.getFechaDevolucionPrevista()))
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DevolucionRequest {
        private String observaciones;
    }
}
