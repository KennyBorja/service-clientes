package com.littlecaesars.clientes.infrastructure.persistence;

import com.littlecaesars.clientes.domain.model.ClienteEstado;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entidad JPA para persistencia de Clientes.
 * Infrastructure Layer — mapeo a la tabla de base de datos.
 * Separada del Aggregate Root para mantener el dominio libre de anotaciones JPA.
 */
@Entity
@Table(name = "clientes")
public class ClienteJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "telefono", nullable = false, unique = true, length = 15)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private ClienteEstado estado;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    // ─── Constructor vacío requerido por JPA ─────────────────────────────────
    protected ClienteJpaEntity() {}

    public ClienteJpaEntity(String id, String nombre, String apellido,
                             String telefono, String email,
                             ClienteEstado estado, LocalDateTime fechaRegistro) {
        this.id            = id;
        this.nombre        = nombre;
        this.apellido      = apellido;
        this.telefono      = telefono;
        this.email         = email;
        this.estado        = estado;
        this.fechaRegistro = fechaRegistro;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────
    public String getId()                { return id; }
    public String getNombre()            { return nombre; }
    public String getApellido()          { return apellido; }
    public String getTelefono()          { return telefono; }
    public String getEmail()             { return email; }
    public ClienteEstado getEstado()     { return estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }

    // ─── Setters (necesarios para actualizaciones JPA) ───────────────────────
    public void setNombre(String nombre)       { this.nombre = nombre; }
    public void setApellido(String apellido)   { this.apellido = apellido; }
    public void setTelefono(String telefono)   { this.telefono = telefono; }
    public void setEmail(String email)         { this.email = email; }
    public void setEstado(ClienteEstado estado){ this.estado = estado; }
}
