package com.littlecaesars.clientes.domain.model;

import java.util.UUID;

public record ClienteId(UUID value) {

    public ClienteId {
        if (value == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
    }

    public static ClienteId nuevo() {
        return new ClienteId(UUID.randomUUID());
    }

    public static ClienteId desde(String uuid) {
        return new ClienteId(UUID.fromString(uuid));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
