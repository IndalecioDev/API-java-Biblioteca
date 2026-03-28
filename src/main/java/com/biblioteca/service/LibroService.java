package com.biblioteca.service;

import com.biblioteca.dto.LibroDTO;
import com.biblioteca.exception.BusinessException;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public List<LibroDTO.Response> findAll() {
        return libroRepository.findAll().stream()
                .map(LibroDTO.Response::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LibroDTO.Response findById(Long id) {
        return LibroDTO.Response.from(getLibroOrThrow(id));
    }

    @Transactional(readOnly = true)
    public LibroDTO.Response findByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
                .map(LibroDTO.Response::from)
                .orElseThrow(() -> new ResourceNotFoundException("Libro con ISBN " + isbn + " no encontrado"));
    }

    public LibroDTO.Response create(LibroDTO.Request request) {
        if (libroRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("Ya existe un libro con el ISBN: " + request.getIsbn());
        }
        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor", request.getAutorId()));

        Libro libro = Libro.builder()
                .titulo(request.getTitulo())
                .isbn(request.getIsbn())
                .anioPublicacion(request.getAnioPublicacion())
                .genero(request.getGenero())
                .descripcion(request.getDescripcion())
                .totalEjemplares(request.getTotalEjemplares() != null ? request.getTotalEjemplares() : 1)
                .ejemplaresDisponibles(request.getTotalEjemplares() != null ? request.getTotalEjemplares() : 1)
                .autor(autor)
                .build();
        return LibroDTO.Response.from(libroRepository.save(libro));
    }

    public LibroDTO.Response update(Long id, LibroDTO.Request request) {
        Libro libro = getLibroOrThrow(id);

        if (!libro.getIsbn().equals(request.getIsbn()) && libroRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("Ya existe un libro con el ISBN: " + request.getIsbn());
        }

        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor", request.getAutorId()));

        int diferencia = (request.getTotalEjemplares() != null ? request.getTotalEjemplares() : libro.getTotalEjemplares())
                - libro.getTotalEjemplares();

        libro.setTitulo(request.getTitulo());
        libro.setIsbn(request.getIsbn());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setGenero(request.getGenero());
        libro.setDescripcion(request.getDescripcion());
        libro.setAutor(autor);
        if (request.getTotalEjemplares() != null) {
            libro.setTotalEjemplares(request.getTotalEjemplares());
            libro.setEjemplaresDisponibles(Math.max(0, libro.getEjemplaresDisponibles() + diferencia));
        }
        return LibroDTO.Response.from(libroRepository.save(libro));
    }

    public void delete(Long id) {
        Libro libro = getLibroOrThrow(id);
        if (libro.getTotalEjemplares() > libro.getEjemplaresDisponibles()) {
            throw new BusinessException("No se puede eliminar un libro con préstamos activos");
        }
        libroRepository.delete(libro);
    }

    @Transactional(readOnly = true)
    public List<LibroDTO.Response> search(String query) {
        return libroRepository.buscarPorQuery(query).stream()
                .map(LibroDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LibroDTO.Response> findByGenero(Libro.Genero genero) {
        return libroRepository.findByGenero(genero).stream()
                .map(LibroDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LibroDTO.Response> findDisponibles() {
        return libroRepository.findLibrosDisponibles().stream()
                .map(LibroDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LibroDTO.Response> findByAutor(Long autorId) {
        autorRepository.findById(autorId)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", autorId));
        return libroRepository.findByAutorId(autorId).stream()
                .map(LibroDTO.Response::from).collect(Collectors.toList());
    }

    private Libro getLibroOrThrow(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", id));
    }
}
