# 📚 Recursos, Enlaces y Evidencias — Little Caesars BPM

**Estudiante:** Borja Valencia Kenny  
**Servicio:** REST Service de Clientes (`service-clientes`)  
**Proyecto:** Aplicación BPM en Entorno Distribuido  

---

## 🔗 Enlaces del Proyecto y Descripción

| Recurso / Enlace | Descripción y Propósito |
| :--- | :--- |
| **[Repositorio GitHub — `service-clientes`](https://github.com/KennyBorja/service-clientes)** | Repositorio personal e independiente del microservicio de Clientes. Contiene el código fuente completo en Java 21, Spring Boot 3.3.2, arquitectura DDD, pruebas unitarias TDD, configuración de RabbitMQ y las ramas de trabajo (`master`, `desarrollo`, `features`). |
| **[Repositorio GitHub — Proyecto Principal BPM](https://github.com/AlonsoUNSA/Little-Caersars-BPM-Project)** | Repositorio grupal del equipo empresarial Little Caesars. Contiene el diagrama BPMN en Bonita Studio, los conectores REST y todos los servicios enlazados como submódulos Git en la carpeta `services/`. |
| **[Swagger UI — Documentación Interactiva](http://localhost:8081/swagger-ui.html)** | Interfaz web interactiva generada por SpringDoc OpenAPI. Permite probar y validar en tiempo real cada endpoint de la API (`POST`, `GET`, `PUT`, `DELETE`) de forma visual. |
| **[Consola de Base de Datos H2](http://localhost:8081/h2-console)** | Consola de administración web para inspeccionar las tablas físicas y registros de la base de datos relacional H2 en memoria (`jdbc:h2:mem:clientesdb`). |
| **[Endpoint de Salud (Health Check)](http://localhost:8081/api/v1/clientes/health)** | Endpoint ligero que responde un estado HTTP `200 OK` ("UP") para verificar que el servicio está activo y respondiendo solicitudes en el puerto 8081. |

---

## 🖼️ Galería de Evidencias e Imágenes

A continuación se presentan las capturas de pantalla de evidencias de ejecución, análisis de SonarQube, diagramación en Bonita Studio y resultados de pruebas:

### 1. Diagramación y Conectores en Bonita Studio
Captura del modelo del proceso BPMN en Bonita Studio mostrando la configuración del conector REST HTTP en la tarea de negocio.

![Diagrama BPMN y Conector REST](imagenes/WhatsApp%20Image%202026-07-30%20at%209.21.38%20AM.jpeg)

---

### 2. Configuración de Entradas y Salidas en Bonita BPM
Configuración de contratos de entrada, scripts Groovy y mapeo de variables para el registro de clientes.

![Configuración Conector Entrada](imagenes/WhatsApp%20Image%202026-07-30%20at%209.37.16%20AM.jpeg)

---

### 3. Mapeo de Respuestas JSON (Script Groovy)
Script de integración para procesar el `clienteId` retornado por la API REST e inyectarlo en el objeto del negocio.

![Script Groovy de Mapeo JSON](imagenes/WhatsApp%20Image%202026-07-30%20at%2010.08.50%20AM.jpeg)

---

### 4. Análisis de Calidad en SonarQube — Hallazgos y Code Smells
Evidencia del informe de calidad de código fuente analizado por SonarQube.

![Análisis SonarQube](imagenes/WhatsApp%20Image%202026-07-30%20at%2010.20.07%20AM.jpeg)

---

### 5. Detalle de Code Smells e Inspección de Reglas
Inspección de severidades en SonarQube validando la ausencia de vulnerabilidades o errores bloqueantes.

![Code Smells SonarQube](imagenes/WhatsApp%20Image%202026-07-30%20at%2010.20.25%20AM.jpeg)

---

### 6. Ejecución del Proceso y Registro de Evidencias
Instancia activa del proceso de negocio ejecutando el flujo completo de atención al cliente.

![Ejecución de Instancia en Bonita](imagenes/WhatsApp%20Image%202026-07-30%20at%2010.20.54%20AM.jpeg)

---

### 7. Formulario de Datos e Interacción de Usuario
Vista del formulario UI generado en Bonita Studio para la captura de datos del cliente por parte del cajero.

![Formulario de Registro](imagenes/WhatsApp%20Image%202026-07-30%20at%2010.12.25%20AM.jpeg)
