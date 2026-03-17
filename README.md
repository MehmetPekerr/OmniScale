# OmniScale

**OmniScale** is a horizontally scalable, containerized Spring Boot application that demonstrates real-world distributed system design patterns — including load balancing, caching, and persistent storage — all orchestrated with Docker Compose.

---

## Architecture

```
                        ┌─────────────────────────────────────┐
                        │           Docker Network             │
                        │                                      │
          HTTP          │   ┌─────────────────────────┐        │
  Client ──────────────►│   │    Nginx (Port 80)       │        │
                        │   │    Load Balancer         │        │
                        │   └──────────┬──────────────┘        │
                        │              │  Round-Robin           │
                        │    ┌─────────┴──────────┐            │
                        │    │                    │            │
                        │  ┌─▼──────┐        ┌───▼────┐       │
                        │  │  app1  │        │  app2  │       │
                        │  │ :8085  │        │ :8086  │       │
                        │  └───┬────┘        └───┬────┘       │
                        │      │                  │            │
                        │      └────────┬─────────┘            │
                        │               │                      │
                        │   ┌───────────┴───────────┐          │
                        │   │                       │          │
                        │ ┌─▼──────────┐   ┌────────▼───────┐ │
                        │ │ PostgreSQL  │   │     Redis      │ │
                        │ │   :5432     │   │     :6379      │ │
                        │ └────────────┘   └────────────────┘ │
                        └─────────────────────────────────────┘
```

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 3.4 |
| Database | PostgreSQL (via Spring Data JPA) |
| Cache | Redis (via Spring Cache) |
| Load Balancer | Nginx (Round-Robin) |
| Containerization | Docker, Docker Compose |
| Build Tool | Maven |

---

## Features

- **Horizontal Scaling** — Two identical Spring Boot instances run in parallel behind Nginx
- **Load Balancing** — Nginx distributes incoming requests in round-robin fashion
- **Redis Caching** — Reduces database load with in-memory caching layer
- **PostgreSQL Persistence** — Relational data storage with JPA/Hibernate auto-schema management
- **Health Monitoring** — Spring Actuator exposes `/actuator/health` and `/actuator/info` endpoints
- **Multi-Stage Docker Build** — Lightweight production image using Eclipse Temurin JRE

---

## Prerequisites

- [Docker](https://www.docker.com/) & Docker Compose
- Git

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/MehmetPekerr/OmniScale.git
cd OmniScale
```

### 2. Start all services

```bash
docker-compose up --build
```

This will spin up:
- `postgres` — PostgreSQL on port `5432`
- `redis` — Redis on port `6379`
- `app1` — Spring Boot instance on port `8085`
- `app2` — Spring Boot instance on port `8086`
- `nginx` — Load balancer on port `80`

### 3. Test the load balancer

```bash
curl http://localhost/
```

Each request will be routed to either `app1` or `app2`. The response includes the app name so you can observe the round-robin behavior:

```
Uygulama doğru çalışıyor! - app1
Uygulama doğru çalışıyor! - app2
```

### 4. Stop all services

```bash
docker-compose down
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/` | Returns the running app instance name |
| `GET` | `/actuator/health` | Health check status |
| `GET` | `/actuator/info` | Application info |

---

## Configuration

Key configuration is managed via `src/main/resources/application.properties` and overridden at runtime through Docker Compose environment variables.

| Variable | Default | Description |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://postgres:5432/appdb` | PostgreSQL connection URL |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | — | Database password |
| `APP_NAME` | — | Identifies the running instance (app1 / app2) |
| `SERVER_PORT` | `8085` | Spring Boot server port |

---

## Project Structure

```
OmniScale/
├── src/
│   └── main/
│       ├── java/com/example/app/
│       │   └── AppApplication.java       # Application entry point + REST controller
│       └── resources/
│           └── application.properties    # App configuration
├── Dockerfile                            # Multi-stage Docker build
├── docker-compose.yml                    # Full stack orchestration
├── nginx.conf                            # Nginx load balancer config
└── pom.xml                               # Maven dependencies
```

---

## License

This project is open-source and available under the [MIT License](LICENSE).
