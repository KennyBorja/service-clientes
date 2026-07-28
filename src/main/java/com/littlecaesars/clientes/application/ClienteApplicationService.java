package com.littlecaesars.clientes.application;

import com.littlecaesars.clientes.application.dto.ClienteRequestDTO;
import com.littlecaesars.clientes.application.dto.ClienteResponseDTO;
import com.littlecaesars.clientes.application.mapper.ClienteMapper;
import com.littlecaesars.clientes.domain.factory.ClienteFactory;
import com.littlecaesars.clientes.domain.model.Cliente;
import com.littlecaesars.clientes.domain.model.ClienteId;
import com.littlecaesars.clientes.domain.repository.ClienteRepository;
import com.littlecaesars.clientes.domain.service.ClienteDomainService;
import com.littlecaesars.clientes.infrastructure.messaging.ClienteEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ClienteApplicationService {

    private final ClienteRepository     clienteRepository;
    private final ClienteFactory        clienteFactory;
    private final ClienteDomainService  clienteDomainService;
    private final ClienteMapper         clienteMapper;
    private final ClienteEventPublisher eventPublisher;

    public ClienteApplicationService(
            ClienteRepository clienteRepository,
            ClienteFactory clienteFactory,
            ClienteDomainService clienteDomainService,
            ClienteMapper clienteMapper,
            ClienteEventPublisher eventPublisher) {
        this.clienteRepository   = clienteRepository;
        this.clienteFactory      = clienteFactory;
        this.clienteDomainService = clienteDomainService;
        this.clienteMapper       = clienteMapper;
        this.eventPublisher      = eventPublisher;
    }

    public ClienteResponseDTO registrarCliente(ClienteRequestDTO request) {
        clienteDomainService.validarTelefonoUnico(request.telefono());

        Cliente cliente = clienteFactory.crear(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.email()
        );

        Cliente guardado = clienteRepository.guardar(cliente);
        eventPublisher.publicarClienteRegistrado(guardado);
        return clienteMapper.toResponseDTO(guardado);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(String id) {
        Cliente cliente = clienteRepository.buscarPorId(ClienteId.desde(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente no encontrado con ID: " + id));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorTelefono(String telefono) {
        Cliente cliente = clienteRepository.buscarPorTelefono(telefono)
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente no encontrado con teléfono: " + telefono));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.listarTodos()
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    public ClienteResponseDTO actualizarCliente(String id, ClienteRequestDTO request) {
        Cliente cliente = clienteRepository.buscarPorId(ClienteId.desde(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente no encontrado con ID: " + id));

        clienteDomainService.validarClienteActivo(cliente);
        clienteDomainService.validarTelefonoUnicoParaActualizacion(request.telefono(), id);

        cliente.actualizarDatos(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.email()
        );

        return clienteMapper.toResponseDTO(clienteRepository.guardar(cliente));
    }

    public ClienteResponseDTO desactivarCliente(String id) {
        Cliente cliente = clienteRepository.buscarPorId(ClienteId.desde(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente no encontrado con ID: " + id));

        cliente.desactivar();
        return clienteMapper.toResponseDTO(clienteRepository.guardar(cliente));
    }
}
