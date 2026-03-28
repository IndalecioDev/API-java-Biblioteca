package com.biblioteca.repository;

import com.biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByLibroId(Long libroId);
    List<Prestamo> findBySocioId(Long socioId);
    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);

    @Query("SELECT p FROM Prestamo p WHERE p.socio.id = :socioId AND p.estado = 'ACTIVO'")
    List<Prestamo> findPrestamosActivosBySocio(@Param("socioId") Long socioId);

    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ACTIVO' AND p.fechaDevolucionPrevista < :hoy")
    List<Prestamo> findPrestamosVencidos(@Param("hoy") LocalDate hoy);

    boolean existsByLibroIdAndSocioIdAndEstado(Long libroId, Long socioId, Prestamo.EstadoPrestamo estado);

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.libro.id = :libroId AND p.estado = 'ACTIVO'")
    long countPrestamosActivosByLibro(@Param("libroId") Long libroId);
}
