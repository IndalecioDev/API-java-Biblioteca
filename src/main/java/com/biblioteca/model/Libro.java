package com.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255)
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Min(value = 1000, message = "El año de publicación no es válido")
    @Max(value = 2100, message = "El año de publicación no es válido")
    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Genero genero;

    @Size(max = 1000)
    @Column(length = 1000)
    private String descripcion;

    @Min(0)
    @Column(name = "total_ejemplares", nullable = false)
    @Builder.Default
    private Integer totalEjemplares = 1;

    @Min(0)
    @Column(name = "ejemplares_disponibles", nullable = false)
    @Builder.Default
    private Integer ejemplaresDisponibles = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    @JsonBackReference
    private Autor autor;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("libro-prestamos")
    @Builder.Default
    private List<Prestamo> prestamos = new ArrayList<>();

    public enum Genero {
        FICCION, NO_FICCION, CIENCIA_FICCION, FANTASIA, ROMANCE,
        MISTERIO, TERROR, BIOGRAFIA, HISTORIA, CIENCIA,
        TECNOLOGIA, ARTE, FILOSOFIA, REALISMO_MAGICO, INFANTIL, OTRO
    }
}
