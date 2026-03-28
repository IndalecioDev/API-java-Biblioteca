package com.biblioteca.service;

import com.biblioteca.dto.SocioDTO;
import com.biblioteca.exception.BusinessException;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Socio;
import com.biblioteca.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SocioService {

    private final SocioRepository socioRepository;

    @Transactional(readOnly = true)
    public List<SocioDTO.Response> findAll() {
        return socioRepository.findAll().stream()
                .map(SocioDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SocioDTO.Response findById(Long id) {
        return SocioDTO.Response.from(getSocioOrThrow(id));
    }

    public SocioDTO.Response create(SocioDTO.Request request) {
        if (socioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un socio con el email: " + request.getEmail());
        }
        Socio socio = Socio.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .build();
        return SocioDTO.Response.from(socioRepository.save(socio));
    }

    public SocioDTO.Response update(Long id, SocioDTO.Request request) {
        Socio socio = getSocioOrThrow(id);
        if (!socio.getEmail().equals(request.getEmail()) && socioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un socio con el email: " + request.getEmail());
        }
        socio.setNombre(request.getNombre());
        socio.setApellido(request.getApellido());
        socio.setEmail(request.getEmail());
        socio.setTelefono(request.getTelefono());
        socio.setDireccion(request.getDireccion());
        return SocioDTO.Response.from(socioRepository.save(socio));
    }

    public SocioDTO.Response toggleActivo(Long id) {
        Socio socio = getSocioOrThrow(id);
        socio.setActivo(!socio.getActivo());
        return SocioDTO.Response.from(socioRepository.save(socio));
    }

    public void delete(Long id) {
        Socio socio = getSocioOrThrow(id);
        boolean tieneActivos = socio.getPrestamos().stream()
                .anyMatch(p -> p.getEstado() == com.biblioteca.model.Prestamo.EstadoPrestamo.ACTIVO);
        if (tieneActivos) {
            throw new BusinessException("No se puede eliminar un socio con préstamos activos");
        }
        socioRepository.delete(socio);
    }

    @Transactional(readOnly = true)
    public List<SocioDTO.Response> search(String query) {
        return socioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(query, query)
                .stream().map(SocioDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SocioDTO.Response> findByActivo(Boolean activo) {
        return socioRepository.findByActivo(activo).stream()
                .map(SocioDTO.Response::from).collect(Collectors.toList());
    }

    private Socio getSocioOrThrow(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", id));
    }
}
