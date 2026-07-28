package com.littlecaesars.clientes.domain.model;

import java.time.LocalDateTime;

/**
 * Aggregate Root del dominio de Clientes.
 * Encapsula toda la lógica de negocio relacionada al cliente.
 * DDD — Aggregate Root.
 */
public class Cliente {

    private final ClienteId id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private ClienteEstado estado;
    private final LocalDateTime fechaRegistro;

    /**
     * Constructor — usar ClienteFactory para crear instancias.
     * Acceso público requerido por ClienteFactory (paquete domain.factory).
     */
    public Cliente(ClienteId id, String nombre, String apellido,
                   String telefono, String email, LocalDateTime fechaRegistro) {
        this.id            = id;
        this.nombre        = nombre;
        this.apellido      = apellido;
        this.telefono      = telefono;
        this.email         = email;
        this.estado        = ClienteEstado.ACTIVO;
        this.fechaRegistro = fechaRegistro;
    }

    // ─── Reglas de Negocio ──────────────────────────────────────────────────

    /**
     * Actualiza los datos del cliente.
     * Valida que el nombre no sea nulo ni vacío.
     */
    public void actualizarDatos(String nombre, String apellido, String telefono, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.telefono  = telefono;
        this.email     = email;
    }

    /**
     * Desactiva el cliente en lugar de eliminarlo físicamente.
     */
    public void desactivar() {
        if (this.estado == ClienteEstado.INACTIVO) {
            throw new IllegalStateException("El cliente ya se encuentra inactivo");
        }
        this.estado = ClienteEstado.INACTIVO;
    }

    /** Verifica si el cliente está activo */
    public boolean estaActivo() {
        return this.estado == ClienteEstado.ACTIVO;
    }

    // ─── Getters ─────────────────────────────────────────────────────────────

    public ClienteId getId()               { return id; }
    public String getNombre()              { return nombre; }
    public String getApellido()            { return apellido; }
    public String getTelefono()            { return telefono; }
    public String getEmail()               { return email; }
    public ClienteEstado getEstado()       { return estado; }
    public LocalDateTime getFechaRegistro(){ return fechaRegistro; }
}
