package com.biblioteca.dto;

import com.biblioteca.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class AutorDTO {

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

        @Size(max = 100)
        private String nacionalidad;

        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        private LocalDate fechaNacimiento;

        @Size(max = 1000)
        private String biografia;
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
        private String nacionalidad;
        private LocalDate fechaNacimiento;
        private String biografia;
        private Integer totalLibros;

        public static Response from(Autor autor) {
            return Response.builder()
                    .id(autor.getId())
                    .nombre(autor.getNombre())
                    .apellido(autor.getApellido())
                    .nacionalidad(autor.getNacionalidad())
                    .fechaNacimiento(autor.getFechaNacimiento())
                    .biografia(autor.getBiografia())
                    .totalLibros(autor.getLibros() != null ? autor.getLibros().size() : 0)
                    .build();
        }
    }
}
