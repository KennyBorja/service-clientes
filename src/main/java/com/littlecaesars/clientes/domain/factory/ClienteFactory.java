package com.littlecaesars.clientes.domain.factory;

import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.model.ClienteId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClienteFactory {

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
