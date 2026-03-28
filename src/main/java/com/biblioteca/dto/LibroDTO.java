package com.biblioteca.dto;

import com.biblioteca.model.Libro;
import jakarta.validation.constraints.*;
import lombok.*;

public class LibroDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255)
        private String titulo;

        @NotBlank(message = "El ISBN es obligatorio")
        @Size(max = 20)
        private String isbn;

        @Min(value = 1000, message = "Año de publicación no válido")
        @Max(value = 2100, message = "Año de publicación no válido")
        private Integer anioPublicacion;

        private Libro.Genero genero;

        @Size(max = 1000)
        private String descripcion;

        @Min(1)
        private Integer totalEjemplares;

        @NotNull(message = "El ID del autor es obligatorio")
        private Long autorId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String titulo;
        private String isbn;
        private Integer anioPublicacion;
        private Libro.Genero genero;
        private String descripcion;
        private Integer totalEjemplares;
        private Integer ejemplaresDisponibles;
        private Boolean disponible;
        private AutorDTO.Response autor;

        public static Response from(Libro libro) {
            return Response.builder()
                    .id(libro.getId())
                    .titulo(libro.getTitulo())
                    .isbn(libro.getIsbn())
                    .anioPublicacion(libro.getAnioPublicacion())
                    .genero(libro.getGenero())
                    .descripcion(libro.getDescripcion())
                    .totalEjemplares(libro.getTotalEjemplares())
                    .ejemplaresDisponibles(libro.getEjemplaresDisponibles())
                    .disponible(libro.getEjemplaresDisponibles() > 0)
                    .autor(AutorDTO.Response.from(libro.getAutor()))
                    .build();
        }
    }
}
