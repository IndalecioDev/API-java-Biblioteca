package com.biblioteca.controller;

import com.biblioteca.dto.ApiResponse;
import com.biblioteca.dto.AutorDTO;
import com.biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "Gestión de autores de la biblioteca")
public class AutorController {

    private final AutorService autorService;

    @GetMapping
    @Operation(summary = "Listar todos los autores")
    public ResponseEntity<ApiResponse<List<AutorDTO.Response>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(autorService.findAll()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener autor por ID")
    public ResponseEntity<ApiResponse<AutorDTO.Response>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(autorService.findById(id)));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar autores por nombre o apellido")
    public ResponseEntity<ApiResponse<List<AutorDTO.Response>>> search(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.ok(autorService.search(q)));
    }

    @GetMapping("/nacionalidad/{nacionalidad}")
    @Operation(summary = "Filtrar autores por nacionalidad")
    public ResponseEntity<ApiResponse<List<AutorDTO.Response>>> findByNacionalidad(@PathVariable String nacionalidad) {
        return ResponseEntity.ok(ApiResponse.ok(autorService.findByNacionalidad(nacionalidad)));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo autor")
    public ResponseEntity<ApiResponse<AutorDTO.Response>> create(@Valid @RequestBody AutorDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Autor registrado correctamente", autorService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un autor")
    public ResponseEntity<ApiResponse<AutorDTO.Response>> update(
            @PathVariable Long id, @Valid @RequestBody AutorDTO.Request request) {
        return ResponseEntity.ok(ApiResponse.ok("Autor actualizado correctamente", autorService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un autor")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Autor eliminado correctamente", null));
    }
}
