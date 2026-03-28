package com.biblioteca.service;

import com.biblioteca.dto.PrestamoDTO;
import com.biblioteca.exception.BusinessException;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Socio;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final SocioRepository socioRepository;

    private static final int DIAS_PRESTAMO_DEFAULT = 15;
    private static final int MAX_PRESTAMOS_SOCIO = 3;

    @Transactional(readOnly = true)
    public List<PrestamoDTO.Response> findAll() {
        return prestamoRepository.findAll().stream()
                .map(PrestamoDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PrestamoDTO.Response findById(Long id) {
        return PrestamoDTO.Response.from(getPrestamoOrThrow(id));
    }

    public PrestamoDTO.Response crear(PrestamoDTO.Request request) {
        Libro libro = libroRepository.findById(request.getLibroId())
                .orElseThrow(() -> new ResourceNotFoundException("Libro", request.getLibroId()));
        Socio socio = socioRepository.findById(request.getSocioId())
                .orElseThrow(() -> new ResourceNotFoundException("Socio", request.getSocioId()));

        if (!socio.getActivo()) {
            throw new BusinessException("El socio no está activo y no puede realizar préstamos");
        }
        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new BusinessException("No hay ejemplares disponibles del libro: " + libro.getTitulo());
        }

        List<Prestamo> activos = prestamoRepository.findPrestamosActivosBySocio(socio.getId());
        if (activos.size() >= MAX_PRESTAMOS_SOCIO) {
            throw new BusinessException("El socio ha alcanzado el límite de " + MAX_PRESTAMOS_SOCIO + " préstamos activos");
        }

        boolean yaTiene = prestamoRepository.existsByLibroIdAndSocioIdAndEstado(
                libro.getId(), socio.getId(), Prestamo.EstadoPrestamo.ACTIVO);
        if (yaTiene) {
            throw new BusinessException("El socio ya tiene un préstamo activo de este libro");
        }

        LocalDate devolucion = request.getFechaDevolucionPrevista() != null
                ? request.getFechaDevolucionPrevista()
                : LocalDate.now().plusDays(DIAS_PRESTAMO_DEFAULT);

        Prestamo prestamo = Prestamo.builder()
                .libro(libro)
                .socio(socio)
                .fechaDevolucionPrevista(devolucion)
                .observaciones(request.getObservaciones())
                .build();

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        return PrestamoDTO.Response.from(prestamoRepository.save(prestamo));
    }

    public PrestamoDTO.Response devolver(Long id, PrestamoDTO.DevolucionRequest request) {
        Prestamo prestamo = getPrestamoOrThrow(id);

        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.DEVUELTO) {
            throw new BusinessException("Este préstamo ya fue devuelto");
        }

        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucionReal(LocalDate.now());
        if (request != null && request.getObservaciones() != null) {
            prestamo.setObservaciones(request.getObservaciones());
        }

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        return PrestamoDTO.Response.from(prestamoRepository.save(prestamo));
    }

    public PrestamoDTO.Response renovar(Long id) {
        Prestamo prestamo = getPrestamoOrThrow(id);

        if (prestamo.getEstado() != Prestamo.EstadoPrestamo.ACTIVO) {
            throw new BusinessException("Solo se pueden renovar préstamos activos");
        }

        prestamo.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista().plusDays(DIAS_PRESTAMO_DEFAULT));
        prestamo.setEstado(Prestamo.EstadoPrestamo.RENOVADO);

        return PrestamoDTO.Response.from(prestamoRepository.save(prestamo));
    }

    @Transactional(readOnly = true)
    public List<PrestamoDTO.Response> findBySocio(Long socioId) {
        socioRepository.findById(socioId)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", socioId));
        return prestamoRepository.findBySocioId(socioId).stream()
                .map(PrestamoDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrestamoDTO.Response> findByLibro(Long libroId) {
        libroRepository.findById(libroId)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", libroId));
        return prestamoRepository.findByLibroId(libroId).stream()
                .map(PrestamoDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrestamoDTO.Response> findVencidos() {
        return prestamoRepository.findPrestamosVencidos(LocalDate.now()).stream()
                .map(PrestamoDTO.Response::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrestamoDTO.Response> findByEstado(Prestamo.EstadoPrestamo estado) {
        return prestamoRepository.findByEstado(estado).stream()
                .map(PrestamoDTO.Response::from).collect(Collectors.toList());
    }

    private Prestamo getPrestamoOrThrow(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo", id));
    }
}
