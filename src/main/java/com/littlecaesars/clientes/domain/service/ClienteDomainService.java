package com.littlecaesars.clientes.domain.service;

import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de dominio para reglas de negocio que involucran
 * múltiples entidades o lógica que no pertenece al Aggregate Root.
 * DDD — Domain Service.
 */
@Service
public class ClienteDomainService {

    private final ClienteRepository clienteRepository;

    public ClienteDomainService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Verifica que el teléfono no esté registrado ya por otro cliente.
     * Regla de negocio: el teléfono es único en el sistema.
     *
     * @param telefono teléfono a validar
     * @throws IllegalStateException si ya existe un cliente con ese teléfono
     */
    public void validarTelefonoUnico(String telefono) {
        if (clienteRepository.existePorTelefono(telefono)) {
            throw new IllegalStateException(
                    "Ya existe un cliente registrado con el teléfono: " + telefono
            );
        }
    }

    /**
     * Valida que el teléfono sea único excluyendo al cliente actual (para updates).
     */
    public void validarTelefonoUnicoParaActualizacion(String telefono, String clienteId) {
        clienteRepository.buscarPorTelefono(telefono).ifPresent(existente -> {
            if (!existente.getId().toString().equals(clienteId)) {
                throw new IllegalStateException(
                        "El teléfono " + telefono + " ya pertenece a otro cliente"
                );
            }
        });
    }

    /**
     * Verifica que el cliente esté activo para operar con él.
     */
    public void validarClienteActivo(Cliente cliente) {
        if (!cliente.estaActivo()) {
            throw new IllegalStateException(
                    "El cliente con ID " + cliente.getId() + " no está activo"
            );
        }
    }
}
