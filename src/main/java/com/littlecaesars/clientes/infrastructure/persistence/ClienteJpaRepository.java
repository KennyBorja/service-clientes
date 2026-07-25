package com.littlecaesars.clientes.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para ClienteJpaEntity.
 * Infrastructure Layer — acceso directo a base de datos.
 */
public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, String> {

    Optional<ClienteJpaEntity> findByTelefono(String telefono);

    boolean existsByTelefono(String telefono);
}
