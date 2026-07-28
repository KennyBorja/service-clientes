package com.littlecaesars.clientes.domain.model;

import java.time.LocalDateTime;

public class Cliente {

    private final ClienteId id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private ClienteEstado estado;
    private final LocalDateTime fechaRegistro;

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

    public void actualizarDatos(String nombre, String apellido, String telefono, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        this.nombre   = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email    = email;
    }

    public void desactivar() {
        if (this.estado == ClienteEstado.INACTIVO) {
            throw new IllegalStateException("El cliente ya se encuentra inactivo");
        }
        this.estado = ClienteEstado.INACTIVO;
    }

    public boolean estaActivo() {
        return this.estado == ClienteEstado.ACTIVO;
    }

    public ClienteId getId()                { return id; }
    public String getNombre()               { return nombre; }
    public String getApellido()             { return apellido; }
    public String getTelefono()             { return telefono; }
    public String getEmail()                { return email; }
    public ClienteEstado getEstado()        { return estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
}
