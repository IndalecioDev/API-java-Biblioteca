package com.biblioteca.controller;

import com.biblioteca.dto.ApiResponse;
import com.biblioteca.dto.SocioDTO;
import com.biblioteca.service.SocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/socios")
@RequiredArgsConstructor
@Tag(name = "Socios", description = "Gestión de socios de la biblioteca")
public class SocioController {

    private final SocioService socioService;

    @GetMapping
    @Operation(summary = "Listar todos los socios")
    public ResponseEntity<ApiResponse<List<SocioDTO.Response>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(socioService.findAll()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener socio por ID")
    public ResponseEntity<ApiResponse<SocioDTO.Response>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(socioService.findById(id)));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar socios por nombre o apellido")
    public ResponseEntity<ApiResponse<List<SocioDTO.Response>>> search(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.ok(socioService.search(q)));
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar socios activos")
    public ResponseEntity<ApiResponse<List<SocioDTO.Response>>> findActivos() {
        return ResponseEntity.ok(ApiResponse.ok(socioService.findByActivo(true)));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo socio")
    public ResponseEntity<ApiResponse<SocioDTO.Response>> create(@Valid @RequestBody SocioDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Socio registrado correctamente", socioService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un socio")
    public ResponseEntity<ApiResponse<SocioDTO.Response>> update(
            @PathVariable Long id, @Valid @RequestBody SocioDTO.Request request) {
        return ResponseEntity.ok(ApiResponse.ok("Socio actualizado correctamente", socioService.update(id, request)));
    }

    @PatchMapping("/{id}/toggle-activo")
    @Operation(summary = "Activar o desactivar un socio")
    public ResponseEntity<ApiResponse<SocioDTO.Response>> toggleActivo(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Estado del socio actualizado", socioService.toggleActivo(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un socio")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        socioService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Socio eliminado correctamente", null));
    }
}
