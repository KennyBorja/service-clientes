package com.littlecaesars.clientes.application.mapper;

import com.littlecaesars.clientes.application.dto.ClienteResponseDTO;
import com.littlecaesars.clientes.domain.model.Cliente;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre el Aggregate Root Cliente y los DTOs.
 * Application Layer — evita exponer el dominio directamente.
 */
@Component
public class ClienteMapper {

    /**
     * Convierte un Aggregate Root Cliente a su DTO de respuesta.
     */
    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId().toString(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getEstado().name(),
                cliente.getFechaRegistro()
        );
    }
}
