package com.biblioteca.controller;

import com.biblioteca.dto.ApiResponse;
import com.biblioteca.dto.PrestamoDTO;
import com.biblioteca.model.Prestamo;
import com.biblioteca.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
@Tag(name = "Préstamos", description = "Gestión de préstamos y devoluciones")
public class PrestamoController {

    private final PrestamoService prestamoService;

    @GetMapping
    @Operation(summary = "Listar todos los préstamos")
    public ResponseEntity<ApiResponse<List<PrestamoDTO.Response>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findAll()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener préstamo por ID")
    public ResponseEntity<ApiResponse<PrestamoDTO.Response>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findById(id)));
    }

    @GetMapping("/socio/{socioId}")
    @Operation(summary = "Listar préstamos de un socio")
    public ResponseEntity<ApiResponse<List<PrestamoDTO.Response>>> findBySocio(@PathVariable Long socioId) {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findBySocio(socioId)));
    }

    @GetMapping("/libro/{libroId}")
    @Operation(summary = "Listar préstamos de un libro")
    public ResponseEntity<ApiResponse<List<PrestamoDTO.Response>>> findByLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findByLibro(libroId)));
    }

    @GetMapping("/vencidos")
    @Operation(summary = "Listar préstamos vencidos")
    public ResponseEntity<ApiResponse<List<PrestamoDTO.Response>>> findVencidos() {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findVencidos()));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtrar préstamos por estado")
    public ResponseEntity<ApiResponse<List<PrestamoDTO.Response>>> findByEstado(@PathVariable Prestamo.EstadoPrestamo estado) {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findByEstado(estado)));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo préstamo")
    public ResponseEntity<ApiResponse<PrestamoDTO.Response>> crear(@Valid @RequestBody PrestamoDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Préstamo registrado correctamente", prestamoService.crear(request)));
    }

    @PatchMapping("/{id}/devolver")
    @Operation(summary = "Registrar devolución de un préstamo")
    public ResponseEntity<ApiResponse<PrestamoDTO.Response>> devolver(
            @PathVariable Long id, @RequestBody(required = false) PrestamoDTO.DevolucionRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Devolución registrada correctamente", prestamoService.devolver(id, request)));
    }

    @PatchMapping("/{id}/renovar")
    @Operation(summary = "Renovar un préstamo activo")
    public ResponseEntity<ApiResponse<PrestamoDTO.Response>> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Préstamo renovado correctamente", prestamoService.renovar(id)));
    }
}
