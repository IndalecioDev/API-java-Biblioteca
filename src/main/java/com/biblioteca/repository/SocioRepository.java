package com.biblioteca.repository;

import com.biblioteca.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    Optional<Socio> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Socio> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
    List<Socio> findByActivo(Boolean activo);
}
