package com.littlecaesars.clientes.infrastructure.persistence;

import com.littlecaesars.clientes.domain.factory.ClienteFactory;
import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.model.ClienteEstado;
import com.littlecaesars.clientes.domain.model.ClienteId;
import com.littlecaesars.clientes.domain.repository.ClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository jpaRepository;
    private final ClienteFactory       clienteFactory;

    public ClienteRepositoryImpl(ClienteJpaRepository jpaRepository,
                                  ClienteFactory clienteFactory) {
        this.jpaRepository = jpaRepository;
        this.clienteFactory = clienteFactory;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        ClienteJpaEntity entity = toEntity(cliente);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Cliente> buscarPorId(ClienteId id) {
        return jpaRepository.findById(id.toString()).map(this::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorTelefono(String telefono) {
        return jpaRepository.findByTelefono(telefono).map(this::toDomain);
    }

    @Override
    public List<Cliente> listarTodos() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existePorTelefono(String telefono) {
        return jpaRepository.existsByTelefono(telefono);
    }

    private ClienteJpaEntity toEntity(Cliente cliente) {
        return new ClienteJpaEntity(
                cliente.getId().toString(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getEstado(),
                cliente.getFechaRegistro()
        );
    }

    private Cliente toDomain(ClienteJpaEntity entity) {
        Cliente cliente = clienteFactory.reconstruir(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getTelefono(),
                entity.getEmail(),
                entity.getFechaRegistro()
        );
        if (entity.getEstado() == ClienteEstado.INACTIVO) {
            cliente.desactivar();
        }
        return cliente;
    }
}
