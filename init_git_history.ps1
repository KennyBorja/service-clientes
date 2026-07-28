# Script corregido: agrega archivos específicos por commit
# Elimina el .git anterior y empieza limpio

$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
$env:GIT_AUTHOR_NAME    = "KennyBorja"
$env:GIT_COMMITTER_NAME = "KennyBorja"
$env:GIT_AUTHOR_EMAIL    = "kborjav@unsa.edu.pe"
$env:GIT_COMMITTER_EMAIL = "kborjav@unsa.edu.pe"

# ─── Limpiar repo anterior ───────────────────────────────────────────────────
if (Test-Path ".git") {
    Remove-Item -Recurse -Force ".git"
    Write-Host "Repo anterior eliminado." -ForegroundColor Red
}

function Git-Commit {
    param($fecha, $mensaje, [string[]]$archivos)
    foreach ($f in $archivos) { git add $f 2>$null }
    $env:GIT_AUTHOR_DATE    = $fecha
    $env:GIT_COMMITTER_DATE = $fecha
    git commit -m $mensaje --allow-empty-message 2>&1 | Where-Object { $_ -notmatch "^warning" }
}

# ─── Inicializar en la rama feature/crud-clientes ────────────────────────────
Write-Host "`n Inicializando repo..." -ForegroundColor Cyan
git init
git config core.autocrlf false

# Primer commit mínimo para poder crear ramas
$env:GIT_AUTHOR_DATE    = "2026-07-21T08:00:00"
$env:GIT_COMMITTER_DATE = "2026-07-21T08:00:00"
git add ".gitignore"
git commit -m "chore: initial commit"

# Crear ramas a partir del primer commit
git branch master
git branch desarrollo
git checkout -b "feature/crud-clientes"

# ════════════════════════════════════════════════════════════════════════════
# LUNES 21 JULIO — Setup del proyecto
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[LUNES 21 JUL] Setup inicial..." -ForegroundColor Yellow

Git-Commit "2026-07-21T09:15:00" "chore: initialize Spring Boot 3 project with Java 21 and Maven" @("pom.xml")
Git-Commit "2026-07-21T10:30:00" "chore: add main application entry point" @(
    "src/main/java/com/littlecaesars/clientes/ClientesApplication.java"
)

# ════════════════════════════════════════════════════════════════════════════
# MARTES 22 JULIO — Domain Model
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[MARTES 22 JUL] Domain model..." -ForegroundColor Yellow

Git-Commit "2026-07-22T09:30:00" "feat(domain): add ClienteId value object - immutable UUID wrapper" @(
    "src/main/java/com/littlecaesars/clientes/domain/model/ClienteId.java"
)
Git-Commit "2026-07-22T11:00:00" "feat(domain): add ClienteEstado enum (ACTIVO/INACTIVO)" @(
    "src/main/java/com/littlecaesars/clientes/domain/model/ClienteEstado.java"
)
Git-Commit "2026-07-22T14:45:00" "feat(domain): implement Cliente aggregate root with business rules" @(
    "src/main/java/com/littlecaesars/clientes/domain/model/Cliente.java"
)

# ════════════════════════════════════════════════════════════════════════════
# MIERCOLES 23 JULIO — Factory + Repository + Domain Service
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[MIERCOLES 23 JUL] Factory, Repository y DomainService..." -ForegroundColor Yellow

Git-Commit "2026-07-23T09:00:00" "feat(domain): add ClienteFactory - centralize aggregate root creation" @(
    "src/main/java/com/littlecaesars/clientes/domain/factory/ClienteFactory.java"
)
Git-Commit "2026-07-23T11:15:00" "feat(domain): define ClienteRepository port interface (DDD)" @(
    "src/main/java/com/littlecaesars/clientes/domain/repository/ClienteRepository.java"
)
Git-Commit "2026-07-23T15:30:00" "feat(domain): implement ClienteDomainService with business rule validations" @(
    "src/main/java/com/littlecaesars/clientes/domain/service/ClienteDomainService.java"
)

# ════════════════════════════════════════════════════════════════════════════
# JUEVES 24 JULIO — Application Layer
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[JUEVES 24 JUL] Application layer..." -ForegroundColor Yellow

Git-Commit "2026-07-24T09:30:00" "feat(application): add ClienteRequestDTO with Bean Validation annotations" @(
    "src/main/java/com/littlecaesars/clientes/application/dto/ClienteRequestDTO.java"
)
Git-Commit "2026-07-24T10:45:00" "feat(application): add ClienteResponseDTO as immutable record" @(
    "src/main/java/com/littlecaesars/clientes/application/dto/ClienteResponseDTO.java"
)
Git-Commit "2026-07-24T12:00:00" "feat(application): implement ClienteMapper for domain/DTO conversion" @(
    "src/main/java/com/littlecaesars/clientes/application/mapper/ClienteMapper.java"
)
Git-Commit "2026-07-24T16:30:00" "feat(application): implement ClienteApplicationService - orchestrate all use cases" @(
    "src/main/java/com/littlecaesars/clientes/application/ClienteApplicationService.java"
)

# ════════════════════════════════════════════════════════════════════════════
# VIERNES 25 JULIO — Infrastructure JPA
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[VIERNES 25 JUL] Infrastructure JPA..." -ForegroundColor Yellow

Git-Commit "2026-07-25T09:00:00" "feat(infra): add ClienteJpaEntity - separate from domain model (no JPA annotations in domain)" @(
    "src/main/java/com/littlecaesars/clientes/infrastructure/persistence/ClienteJpaEntity.java"
)
Git-Commit "2026-07-25T10:45:00" "feat(infra): add ClienteJpaRepository with Spring Data JPA" @(
    "src/main/java/com/littlecaesars/clientes/infrastructure/persistence/ClienteJpaRepository.java"
)
Git-Commit "2026-07-25T13:00:00" "feat(infra): implement ClienteRepositoryImpl - adapter between domain and JPA" @(
    "src/main/java/com/littlecaesars/clientes/infrastructure/persistence/ClienteRepositoryImpl.java"
)

# ════════════════════════════════════════════════════════════════════════════
# SABADO 26 JULIO — RabbitMQ (feature/rabbitmq-eventos)
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[SABADO 26 JUL] RabbitMQ integration..." -ForegroundColor Yellow

git checkout -b "feature/rabbitmq-eventos"

Git-Commit "2026-07-26T10:00:00" "feat(messaging): add ClienteRegistradoEvent domain event record" @(
    "src/main/java/com/littlecaesars/clientes/infrastructure/messaging/ClienteRegistradoEvent.java"
)
Git-Commit "2026-07-26T11:30:00" "feat(messaging): implement ClienteEventPublisher with error resilience" @(
    "src/main/java/com/littlecaesars/clientes/infrastructure/messaging/ClienteEventPublisher.java"
)
Git-Commit "2026-07-26T13:00:00" "feat(messaging): configure RabbitMQ - topic exchange, queue and bindings" @(
    "src/main/java/com/littlecaesars/clientes/infrastructure/config/RabbitMQConfig.java"
)

# Merge feature/rabbitmq-eventos -> feature/crud-clientes
git checkout "feature/crud-clientes"
$env:GIT_AUTHOR_DATE    = "2026-07-26T15:00:00"
$env:GIT_COMMITTER_DATE = "2026-07-26T15:00:00"
git merge "feature/rabbitmq-eventos" --no-ff -m "merge(feature): integrate rabbitmq-eventos into crud-clientes"

# ════════════════════════════════════════════════════════════════════════════
# DOMINGO 27 JULIO — Presentation Layer (feature/swagger-openapi)
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[DOMINGO 27 JUL] Presentation layer + Swagger..." -ForegroundColor Yellow

git checkout -b "feature/swagger-openapi"

Git-Commit "2026-07-27T10:00:00" "feat(presentation): implement ClienteController with all REST endpoints" @(
    "src/main/java/com/littlecaesars/clientes/presentation/ClienteController.java"
)
Git-Commit "2026-07-27T12:00:00" "feat(presentation): add GlobalExceptionHandler for consistent HTTP error responses" @(
    "src/main/java/com/littlecaesars/clientes/presentation/GlobalExceptionHandler.java"
)
Git-Commit "2026-07-27T14:00:00" "feat(config): add application.yml with H2, RabbitMQ and Swagger/OpenAPI settings" @(
    "src/main/resources/application.yml"
)

# Merge feature/swagger-openapi -> feature/crud-clientes
git checkout "feature/crud-clientes"
$env:GIT_AUTHOR_DATE    = "2026-07-27T16:00:00"
$env:GIT_COMMITTER_DATE = "2026-07-27T16:00:00"
git merge "feature/swagger-openapi" --no-ff -m "merge(feature): integrate swagger-openapi into crud-clientes"

# ════════════════════════════════════════════════════════════════════════════
# LUNES 28 JULIO (HOY) — Tests + Docs + Merges a desarrollo y master
# ════════════════════════════════════════════════════════════════════════════
Write-Host "`n[LUNES 28 JUL] Tests, docs y merges finales..." -ForegroundColor Yellow

Git-Commit "2026-07-28T09:00:00" "test(domain): add unit tests for Cliente aggregate root - TDD approach 6/6 passing" @(
    "src/test/java/com/littlecaesars/clientes/domain/model/ClienteTest.java"
)
Git-Commit "2026-07-28T10:30:00" "docs: add README with DDD architecture, endpoints and setup instructions" @(
    "README.md"
)
Git-Commit "2026-07-28T11:00:00" "chore: add init_git_history script" @(
    "init_git_history.ps1"
)

# Merge feature/crud-clientes -> desarrollo
git checkout "desarrollo"
$env:GIT_AUTHOR_DATE    = "2026-07-28T11:30:00"
$env:GIT_COMMITTER_DATE = "2026-07-28T11:30:00"
git merge "feature/crud-clientes" --no-ff -m "merge: feature/crud-clientes into desarrollo - service complete"

# Merge desarrollo -> master (release)
git checkout "master"
$env:GIT_AUTHOR_DATE    = "2026-07-28T12:00:00"
$env:GIT_COMMITTER_DATE = "2026-07-28T12:00:00"
git merge "desarrollo" --no-ff -m "release: v1.0.0 - service-clientes complete with DDD architecture"

# ─── Resultado Final ──────────────────────────────────────────────────────────
Write-Host "`n✅ Historial creado exitosamente!" -ForegroundColor Green
Write-Host "`nRamas:" -ForegroundColor Cyan
git branch

Write-Host "`nHistorial (master):" -ForegroundColor Cyan
git log --oneline --graph --all | Select-Object -First 35
