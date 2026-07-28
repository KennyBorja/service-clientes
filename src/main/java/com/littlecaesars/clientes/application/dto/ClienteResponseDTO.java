package com.littlecaesars.clientes.application.dto;

import java.time.LocalDateTime;

/**
 * DTO de respuesta que expone los datos del cliente al exterior.
 * Application Layer — salida de datos hacia el controlador.
 */
public record ClienteResponseDTO(
        String id,
        String nombre,
        String apellido,
        String telefono,
        String email,
        String estado,
        LocalDateTime fechaRegistro
) {}
