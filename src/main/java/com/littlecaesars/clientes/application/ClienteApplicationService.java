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

/**
 * Servicio de aplicación — orquesta casos de uso del dominio.
 * Application Layer — coordina Domain Services, Factory, Repository y eventos.
 */
@Service
@Transactional
public class ClienteApplicationService {

    private final ClienteRepository      clienteRepository;
    private final ClienteFactory         clienteFactory;
    private final ClienteDomainService   clienteDomainService;
    private final ClienteMapper          clienteMapper;
    private final ClienteEventPublisher  eventPublisher;

    public ClienteApplicationService(
            ClienteRepository clienteRepository,
            ClienteFactory clienteFactory,
            ClienteDomainService clienteDomainService,
            ClienteMapper clienteMapper,
            ClienteEventPublisher eventPublisher) {
        this.clienteRepository    = clienteRepository;
        this.clienteFactory       = clienteFactory;
        this.clienteDomainService = clienteDomainService;
        this.clienteMapper        = clienteMapper;
        this.eventPublisher       = eventPublisher;
    }

    // ─── Caso de uso: Registrar Cliente ──────────────────────────────────────

    public ClienteResponseDTO registrarCliente(ClienteRequestDTO request) {
        // 1. Regla de negocio: teléfono único
        clienteDomainService.validarTelefonoUnico(request.telefono());

        // 2. Crear el Aggregate Root via Factory
        Cliente cliente = clienteFactory.crear(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.email()
        );

        // 3. Persistir
        Cliente guardado = clienteRepository.guardar(cliente);

        // 4. Publicar evento a RabbitMQ
        eventPublisher.publicarClienteRegistrado(guardado);

        return clienteMapper.toResponseDTO(guardado);
    }

    // ─── Caso de uso: Buscar por ID ───────────────────────────────────────────

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(String id) {
        Cliente cliente = clienteRepository.buscarPorId(ClienteId.desde(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente no encontrado con ID: " + id));
        return clienteMapper.toResponseDTO(cliente);
    }

    // ─── Caso de uso: Buscar por Teléfono ────────────────────────────────────

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorTelefono(String telefono) {
        Cliente cliente = clienteRepository.buscarPorTelefono(telefono)
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente no encontrado con teléfono: " + telefono));
        return clienteMapper.toResponseDTO(cliente);
    }

    // ─── Caso de uso: Listar Todos ────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.listarTodos()
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    // ─── Caso de uso: Actualizar Cliente ─────────────────────────────────────

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

    // ─── Caso de uso: Desactivar Cliente ─────────────────────────────────────

    public ClienteResponseDTO desactivarCliente(String id) {
        Cliente cliente = clienteRepository.buscarPorId(ClienteId.desde(id))
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente no encontrado con ID: " + id));

        cliente.desactivar();
        return clienteMapper.toResponseDTO(clienteRepository.guardar(cliente));
    }
}
