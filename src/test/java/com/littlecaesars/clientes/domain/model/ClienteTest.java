package com.littlecaesars.clientes.domain.model;

import com.littlecaesars.clientes.domain.factory.ClienteFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios del Aggregate Root Cliente.
 * TDD — prueba las reglas de negocio del dominio.
 */
@DisplayName("Cliente - Pruebas del Aggregate Root")
class ClienteTest {

    private ClienteFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ClienteFactory();
    }

    @Test
    @DisplayName("Debe crear un cliente activo con ID autogenerado")
    void debeCrearClienteActivo() {
        Cliente cliente = factory.crear("Juan", "Pérez", "987654321", "juan@email.com");

        assertNotNull(cliente.getId());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Pérez", cliente.getApellido());
        assertEquals(ClienteEstado.ACTIVO, cliente.getEstado());
        assertNotNull(cliente.getFechaRegistro());
    }

    @Test
    @DisplayName("Debe desactivar un cliente activo")
    void debeDesactivarClienteActivo() {
        Cliente cliente = factory.crear("Ana", "Gómez", "912345678", "ana@email.com");

        cliente.desactivar();

        assertEquals(ClienteEstado.INACTIVO, cliente.getEstado());
        assertFalse(cliente.estaActivo());
    }

    @Test
    @DisplayName("No debe desactivar un cliente ya inactivo")
    void noDebeDesactivarClienteYaInactivo() {
        Cliente cliente = factory.crear("Luis", "Ríos", "911111111", "luis@email.com");
        cliente.desactivar();

        assertThrows(IllegalStateException.class, cliente::desactivar);
    }

    @Test
    @DisplayName("Debe actualizar los datos del cliente")
    void debeActualizarDatosCliente() {
        Cliente cliente = factory.crear("Carlos", "Vega", "922222222", "carlos@email.com");

        cliente.actualizarDatos("Carlos A.", "Vega Torres", "933333333", "carlos.a@email.com");

        assertEquals("Carlos A.", cliente.getNombre());
        assertEquals("Vega Torres", cliente.getApellido());
        assertEquals("933333333", cliente.getTelefono());
    }

    @Test
    @DisplayName("No debe crear cliente con nombre vacío")
    void noDebeCrearClienteConNombreVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.crear("", "Pérez", "944444444", "email@test.com"));
    }

    @Test
    @DisplayName("No debe crear cliente con teléfono nulo")
    void noDebeCrearClienteConTelefonoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.crear("Juan", "Pérez", null, "email@test.com"));
    }
}
