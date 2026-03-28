package com.biblioteca.controller;

import com.biblioteca.dto.ApiResponse;
import com.biblioteca.dto.LibroDTO;
import com.biblioteca.model.Libro;
import com.biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Gestión del catálogo de libros")
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    @Operation(summary = "Listar todos los libros")
    public ResponseEntity<ApiResponse<List<LibroDTO.Response>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findAll()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro por ID")
    public ResponseEntity<ApiResponse<LibroDTO.Response>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findById(id)));
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Obtener libro por ISBN")
    public ResponseEntity<ApiResponse<LibroDTO.Response>> findByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findByIsbn(isbn)));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar libros por título, autor o ISBN")
    public ResponseEntity<ApiResponse<List<LibroDTO.Response>>> search(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.search(q)));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar libros con ejemplares disponibles")
    public ResponseEntity<ApiResponse<List<LibroDTO.Response>>> findDisponibles() {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findDisponibles()));
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Filtrar libros por género")
    public ResponseEntity<ApiResponse<List<LibroDTO.Response>>> findByGenero(@PathVariable Libro.Genero genero) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findByGenero(genero)));
    }

    @GetMapping("/autor/{autorId}")
    @Operation(summary = "Listar libros de un autor")
    public ResponseEntity<ApiResponse<List<LibroDTO.Response>>> findByAutor(@PathVariable Long autorId) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findByAutor(autorId)));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo libro")
    public ResponseEntity<ApiResponse<LibroDTO.Response>> create(@Valid @RequestBody LibroDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Libro registrado correctamente", libroService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un libro")
    public ResponseEntity<ApiResponse<LibroDTO.Response>> update(
            @PathVariable Long id, @Valid @RequestBody LibroDTO.Request request) {
        return ResponseEntity.ok(ApiResponse.ok("Libro actualizado correctamente", libroService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un libro")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        libroService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Libro eliminado correctamente", null));
    }
}
