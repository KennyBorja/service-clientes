package com.littlecaesars.clientes.domain.service;

import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteDomainService {

    private final ClienteRepository clienteRepository;

    public ClienteDomainService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void validarTelefonoUnico(String telefono) {
        if (clienteRepository.existePorTelefono(telefono)) {
            throw new IllegalStateException(
                    "Ya existe un cliente registrado con el teléfono: " + telefono
            );
        }
    }

    public void validarTelefonoUnicoParaActualizacion(String telefono, String clienteId) {
        clienteRepository.buscarPorTelefono(telefono).ifPresent(existente -> {
            if (!existente.getId().toString().equals(clienteId)) {
                throw new IllegalStateException(
                        "El teléfono " + telefono + " ya pertenece a otro cliente"
                );
            }
        });
    }

    public void validarClienteActivo(Cliente cliente) {
        if (!cliente.estaActivo()) {
            throw new IllegalStateException(
                    "El cliente con ID " + cliente.getId() + " no está activo"
            );
        }
    }
}
