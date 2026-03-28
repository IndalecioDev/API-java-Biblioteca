package com.biblioteca.repository;

import com.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByGenero(Libro.Genero genero);
    List<Libro> findByAutorId(Long autorId);
    List<Libro> findByEjemplaresDisponiblesGreaterThan(int cantidad);

    @Query("SELECT l FROM Libro l WHERE l.ejemplaresDisponibles > 0")
    List<Libro> findLibrosDisponibles();

    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(l.autor.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(l.autor.apellido) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR l.isbn LIKE CONCAT('%', :query, '%')")
    List<Libro> buscarPorQuery(@Param("query") String query);
}
