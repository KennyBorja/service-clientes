package com.littlecaesars.clientes.infrastructure.messaging;

import com.littlecaesars.clientes.domain.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Publicador de eventos al broker RabbitMQ.
 * Infrastructure Layer — publica eventos de dominio al exterior.
 */
@Component
public class ClienteEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(ClienteEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.clientes}")
    private String exchange;

    @Value("${rabbitmq.routing-key.cliente-registrado}")
    private String routingKeyRegistrado;

    public ClienteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publica el evento "cliente.registrado" al broker de mensajes.
     * Otros servicios (Caja, Bonita) pueden suscribirse a este evento.
     *
     * @param cliente el cliente recién registrado
     */
    public void publicarClienteRegistrado(Cliente cliente) {
        ClienteRegistradoEvent evento = new ClienteRegistradoEvent(
                cliente.getId().toString(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getTelefono(),
                cliente.getEmail(),
                LocalDateTime.now()
        );

        try {
            rabbitTemplate.convertAndSend(exchange, routingKeyRegistrado, evento);
            log.info("Evento cliente.registrado publicado para clienteId: {}", cliente.getId());
        } catch (Exception e) {
            // El fallo en mensajería no debe interrumpir el registro del cliente
            log.error("Error al publicar evento RabbitMQ para clienteId: {}. Detalle: {}",
                    cliente.getId(), e.getMessage());
        }
    }
}
