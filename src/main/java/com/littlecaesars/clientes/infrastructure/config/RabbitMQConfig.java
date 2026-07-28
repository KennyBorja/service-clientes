package com.littlecaesars.clientes.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ: Exchange, Queue y Bindings.
 * Infrastructure Layer.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.clientes}")
    private String exchange;

    @Value("${rabbitmq.queue.cliente-registrado}")
    private String queueClienteRegistrado;

    @Value("${rabbitmq.routing-key.cliente-registrado}")
    private String routingKeyRegistrado;

    /** Exchange de tipo Topic para eventos de clientes */
    @Bean
    public TopicExchange clientesExchange() {
        return new TopicExchange(exchange, true, false);
    }

    /** Cola que recibe el evento cliente.registrado */
    @Bean
    public Queue queueClienteRegistrado() {
        return QueueBuilder.durable(queueClienteRegistrado).build();
    }

    /** Binding: conecta la cola al exchange con la routing key */
    @Bean
    public Binding bindingClienteRegistrado(Queue queueClienteRegistrado,
                                             TopicExchange clientesExchange) {
        return BindingBuilder
                .bind(queueClienteRegistrado)
                .to(clientesExchange)
                .with(routingKeyRegistrado);
    }

    /** Convierte mensajes a JSON automáticamente */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /** Configura el template con el conversor JSON */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
