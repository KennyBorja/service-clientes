package com.littlecaesars.clientes.domain.model;

import java.util.UUID;

/**
 * Value Object que representa el identificador único de un Cliente.
 * Inmutable por diseño — DDD Value Object.
 */
public record ClienteId(UUID value) {

    public ClienteId {
        if (value == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
    }

    /** Genera un nuevo ClienteId aleatorio */
    public static ClienteId nuevo() {
        return new ClienteId(UUID.randomUUID());
    }

    /** Reconstruye un ClienteId desde un String UUID */
    public static ClienteId desde(String uuid) {
        return new ClienteId(UUID.fromString(uuid));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
