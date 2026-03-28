package com.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    @JsonBackReference("libro-prestamos")
    @NotNull
    private Libro libro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    @JsonBackReference("socio-prestamos")
    @NotNull
    private Socio socio;

    @Column(name = "fecha_prestamo", nullable = false)
    @Builder.Default
    private LocalDate fechaPrestamo = LocalDate.now();

    @Column(name = "fecha_devolucion_prevista", nullable = false)
    private LocalDate fechaDevolucionPrevista;

    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoPrestamo estado = EstadoPrestamo.ACTIVO;

    @Column(length = 500)
    private String observaciones;

    public enum EstadoPrestamo {
        ACTIVO, DEVUELTO, VENCIDO, RENOVADO
    }
}
