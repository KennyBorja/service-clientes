package com.littlecaesars.clientes.domain.factory;

import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.model.ClienteId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Factory para la creación de instancias de Cliente.
 * Centraliza la lógica de construcción del Aggregate Root.
 * DDD — Factory Pattern.
 */
@Component
public class ClienteFactory {

    /**
     * Crea un nuevo Cliente con ID autogenerado y fecha de registro actual.
     *
     * @param nombre    Nombre del cliente
     * @param apellido  Apellido del cliente
     * @param telefono  Teléfono único del cliente
     * @param email     Correo electrónico del cliente
     * @return instancia válida de Cliente
     */
    public Cliente crear(String nombre, String apellido, String telefono, String email) {
        validarDatos(nombre, telefono);
        return new Cliente(
                ClienteId.nuevo(),
                nombre,
                apellido,
                telefono,
                email,
                LocalDateTime.now()
        );
    }

    /**
     * Reconstruye un Cliente desde la base de datos (sin generar nuevo ID).
     */
    public Cliente reconstruir(String id, String nombre, String apellido,
                                String telefono, String email, LocalDateTime fechaRegistro) {
        return new Cliente(
                ClienteId.desde(id),
                nombre,
                apellido,
                telefono,
                email,
                fechaRegistro
        );
    }

    private void validarDatos(String nombre, String telefono) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
    }
}
