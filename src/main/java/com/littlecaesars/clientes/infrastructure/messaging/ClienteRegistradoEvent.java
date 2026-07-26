package com.littlecaesars.clientes.infrastructure.messaging;

import java.time.LocalDateTime;

/**
 * Evento de dominio que se publica cuando un cliente es registrado.
 * Consumed by: Servicio de Caja, Bonita BPM (notificaciones).
 */
public record ClienteRegistradoEvent(
        String clienteId,
        String nombre,
        String apellido,
        String telefono,
        String email,
        LocalDateTime timestamp
) {}
