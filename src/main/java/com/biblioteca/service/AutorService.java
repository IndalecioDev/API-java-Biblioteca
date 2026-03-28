package com.biblioteca.service;

import com.biblioteca.dto.AutorDTO;
import com.biblioteca.exception.BusinessException;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Autor;
import com.biblioteca.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AutorService {

    private final AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public List<AutorDTO.Response> findAll() {
        return autorRepository.findAll().stream()
                .map(AutorDTO.Response::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AutorDTO.Response findById(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", id));
        return AutorDTO.Response.from(autor);
    }

    public AutorDTO.Response create(AutorDTO.Request request) {
        if (autorRepository.existsByNombreIgnoreCaseAndApellidoIgnoreCase(
                request.getNombre(), request.getApellido())) {
            throw new BusinessException("Ya existe un autor con ese nombre y apellido");
        }
        Autor autor = Autor.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .nacionalidad(request.getNacionalidad())
                .fechaNacimiento(request.getFechaNacimiento())
                .biografia(request.getBiografia())
                .build();
        return AutorDTO.Response.from(autorRepository.save(autor));
    }

    public AutorDTO.Response update(Long id, AutorDTO.Request request) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", id));
        autor.setNombre(request.getNombre());
        autor.setApellido(request.getApellido());
        autor.setNacionalidad(request.getNacionalidad());
        autor.setFechaNacimiento(request.getFechaNacimiento());
        autor.setBiografia(request.getBiografia());
        return AutorDTO.Response.from(autorRepository.save(autor));
    }

    public void delete(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", id));
        if (!autor.getLibros().isEmpty()) {
            throw new BusinessException("No se puede eliminar un autor con libros registrados");
        }
        autorRepository.delete(autor);
    }

    @Transactional(readOnly = true)
    public List<AutorDTO.Response> search(String query) {
        return autorRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(query, query)
                .stream().map(AutorDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutorDTO.Response> findByNacionalidad(String nacionalidad) {
        return autorRepository.findByNacionalidadIgnoreCase(nacionalidad)
                .stream().map(AutorDTO.Response::from).collect(Collectors.toList());
    }
}
