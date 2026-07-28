package com.littlecaesars.clientes.presentation;

import com.littlecaesars.clientes.application.ClienteApplicationService;
import com.littlecaesars.clientes.application.dto.ClienteRequestDTO;
import com.littlecaesars.clientes.application.dto.ClienteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Clientes.
 * Presentation Layer — expone los endpoints HTTP de la API.
 */
@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "API para la gestión de clientes de Little Caesars")
public class ClienteController {

    private final ClienteApplicationService applicationService;

    public ClienteController(ClienteApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // ─── POST /api/v1/clientes ────────────────────────────────────────────────

    @Operation(summary = "Registrar nuevo cliente",
               description = "Crea un nuevo cliente en el sistema y publica el evento a RabbitMQ")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe un cliente con ese teléfono")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrar(
            @Valid @RequestBody ClienteRequestDTO request) {
        ClienteResponseDTO response = applicationService.registrarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─── GET /api/v1/clientes ─────────────────────────────────────────────────

    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(applicationService.listarTodos());
    }

    // ─── GET /api/v1/clientes/{id} ────────────────────────────────────────────

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(
            @Parameter(description = "UUID del cliente") @PathVariable String id) {
        return ResponseEntity.ok(applicationService.buscarPorId(id));
    }

    // ─── GET /api/v1/clientes/buscar?telefono= ────────────────────────────────

    @Operation(summary = "Buscar cliente por teléfono",
               description = "Usado en tienda para identificar al cliente al momento del pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/buscar")
    public ResponseEntity<ClienteResponseDTO> buscarPorTelefono(
            @Parameter(description = "Número de teléfono del cliente")
            @RequestParam String telefono) {
        return ResponseEntity.ok(applicationService.buscarPorTelefono(telefono));
    }

    // ─── PUT /api/v1/clientes/{id} ────────────────────────────────────────────

    @Operation(summary = "Actualizar datos de un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ClienteRequestDTO request) {
        return ResponseEntity.ok(applicationService.actualizarCliente(id, request));
    }

    // ─── DELETE /api/v1/clientes/{id} ─────────────────────────────────────────

    @Operation(summary = "Desactivar cliente",
               description = "Desactiva el cliente (soft delete) sin eliminar el registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente desactivado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> desactivar(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.desactivarCliente(id));
    }

    // ─── GET /api/v1/clientes/health ──────────────────────────────────────────

    @Operation(summary = "Health check del servicio")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("service-clientes UP ✓");
    }
}
