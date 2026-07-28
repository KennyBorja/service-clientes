package com.littlecaesars.clientes.domain.repository;

import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.model.ClienteId;

import java.util.List;
import java.util.Optional;

/**
 * Puerto (interface) del repositorio de Clientes.
 * Define el contrato que debe cumplir cualquier implementación de persistencia.
 * DDD — Repository Interface (Domain Layer).
 */
public interface ClienteRepository {

    /** Persiste un cliente (crear o actualizar) */
    Cliente guardar(Cliente cliente);

    /** Busca un cliente por su ID */
    Optional<Cliente> buscarPorId(ClienteId id);

    /** Busca un cliente por su número de teléfono */
    Optional<Cliente> buscarPorTelefono(String telefono);

    /** Retorna todos los clientes registrados */
    List<Cliente> listarTodos();

    /** Verifica si ya existe un cliente con ese teléfono */
    boolean existePorTelefono(String telefono);
}
