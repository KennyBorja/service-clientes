# 🍕 service-clientes — Little Caesars BPM

Servicio REST para la gestión de clientes del proyecto **Little Caesars BPM**.
Implementado con **Java 21 + Spring Boot 3 + Maven**.

---

## 👥 Equipo

| Integrante | Servicio |
|-----------|---------|
| [borja valencia kenny] | service-clientes |

**Organización**: Little Caesars (ficticia)  
**Repositorio principal**: [Little-Caersars-BPM-Project](https://github.com/AlonsoUNSA/Little-Caersars-BPM-Project)

---

## 🎯 Propósito

Gestiona el registro, búsqueda y administración de clientes en el flujo BPM de pedidos presenciales en tienda. Se integra con:
- **Bonitasoft** → como tarea automática en el proceso BPMN
- **RabbitMQ** → publica el evento `cliente.registrado` al broker

---

## 🏗️ Arquitectura — Domain-Driven Design (DDD)

```
┌──────────────────────────────────────────┐
│  PRESENTATION    ClienteController       │
├──────────────────────────────────────────┤
│  APPLICATION     ClienteApplicationService│
│                  ClienteDTO / Mapper     │
├──────────────────────────────────────────┤
│  DOMAIN          Cliente (Aggregate Root)│
│                  ClienteId (Value Object)│
│                  ClienteFactory          │
│                  ClienteRepository (port)│
│                  ClienteDomainService    │
├──────────────────────────────────────────┤
│  INFRASTRUCTURE  ClienteRepositoryImpl   │
│                  ClienteJpaEntity        │
│                  ClienteEventPublisher   │
│                  RabbitMQConfig          │
└──────────────────────────────────────────┘
```

---

## 🔌 Endpoints REST

| Método | URL | Descripción |
|--------|-----|-------------|
| `POST` | `/api/v1/clientes` | Registrar nuevo cliente |
| `GET` | `/api/v1/clientes` | Listar todos los clientes |
| `GET` | `/api/v1/clientes/{id}` | Buscar por ID |
| `GET` | `/api/v1/clientes/buscar?telefono=xxx` | Buscar por teléfono |
| `PUT` | `/api/v1/clientes/{id}` | Actualizar datos |
| `DELETE` | `/api/v1/clientes/{id}` | Desactivar cliente |
| `GET` | `/api/v1/clientes/health` | Health check |

### Swagger UI
```
http://localhost:8081/swagger-ui.html
```

---

## 🐇 Evento RabbitMQ

Al registrar un cliente se publica:

```json
Exchange:    clientes.exchange
Routing Key: cliente.registrado

{
  "clienteId":  "uuid",
  "nombre":     "Juan",
  "apellido":   "Pérez",
  "telefono":   "987654321",
  "email":      "juan@email.com",
  "timestamp":  "2024-01-15T10:30:00"
}
```

---

## 🚀 Ejecución

### Prerrequisitos
- Java 21
- Maven 3.9+
- RabbitMQ (opcional, el servicio funciona sin él)

### Levantar el servicio

```bash
# Clonar
git clone git@github.com:TU_USUARIO/service-clientes.git
cd service-clientes

# Ejecutar
mvn spring-boot:run
```

### URLs disponibles

| Recurso | URL |
|---------|-----|
| API | `http://localhost:8081/api/v1/clientes` |
| Swagger | `http://localhost:8081/swagger-ui.html` |
| H2 Console | `http://localhost:8081/h2-console` |

---

## 🧪 Tests

```bash
mvn test
```

---

## 🌿 Ramas

```
master        → código estable
desarrollo    → integración de features
feature/crud-clientes    → CRUD de clientes
feature/rabbitmq-eventos → eventos RabbitMQ
feature/swagger-openapi  → documentación API
```
