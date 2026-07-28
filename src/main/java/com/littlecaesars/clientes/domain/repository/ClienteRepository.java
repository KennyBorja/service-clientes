package com.littlecaesars.clientes.domain.repository;

import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.model.ClienteId;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Cliente guardar(Cliente cliente);

    Optional<Cliente> buscarPorId(ClienteId id);

    Optional<Cliente> buscarPorTelefono(String telefono);

    List<Cliente> listarTodos();

    boolean existePorTelefono(String telefono);
}
