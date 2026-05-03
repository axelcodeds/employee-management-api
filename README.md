
# 📌 Employee Management System API

## 🚀 Overview
A production-grade backend for managing employees and departments with strong focus on security, scalability, and maintainability. This project demonstrates real-world backend engineering practices: layered architecture, DTO separation, JWT-based authentication, role-based authorization, automated testing, CI/CD, Dockerization and production deployment.

> All roadmap tasks are complete. The application is deployed to a DigitalOcean droplet and served at the live URL below.

## 🎥 Demo & Live
- **Live API / Swagger UI:** https://ems.axeldiego.dev/api/ui

### Demo credentials
- Regular user: `user` / `user123` (can call read endpoints)


## ✅ Roadmap & Status
- Spiral 1 → API Quality: Complete
- Spiral 2 → Domain Improvements: Complete
- Spiral 3 → Security: Complete
- Spiral 4 → Infrastructure (Swagger, Docker, Tests, CI/CD): Complete
- Spiral 5 → Deployment: Complete (DigitalOcean droplet)

## ✨ Key Features
- JWT-based stateless authentication
- Role-based access control (ADMIN / USER)
- Pagination, sorting and filtering for listing employees
- Audit fields (createdAt, updatedAt)
- Comprehensive unit tests and in-memory H2 test profile
- OpenAPI / Swagger UI for interactive API exploration
- Dockerized build and GitHub Actions CI/CD pipelines

## 🏗 Architecture
- Layered: Controller → Service → Repository
- DTOs used to decouple API contracts from persistence models
- Centralized security and exception handling

## 🔐 Security Highlights
- Login returns a JWT; include it in requests as `Authorization: Bearer <token>`
- Public endpoints: `/api/auth/**`, `/api/health`, `/api/docs/**`, `/api/ui/**`, `/v3/api-docs/**`
- Administrative actions (create/update/delete) are restricted to `ADMIN` via `@PreAuthorize`
- Passwords are hashed with BCrypt

## 🛠 Tech Stack
**Backend**: Java 21, Spring Boot, Spring WebMVC, Spring Data JPA, Spring Security

**Auth**: JWT (jjwt), BCrypt

**Database**: PostgreSQL (production), H2 (tests)

**Tools**: Lombok, Bean Validation, Springdoc OpenAPI, Docker, GitHub Actions

## 📦 Installation & Setup
**Prerequisites**
- Java 21
- PostgreSQL (or Docker)

**Environment variables** (example)
- `SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ems`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=postgres`
- `SPRING_JPA_HIBERNATE_DDL_AUTO=update`
- `JWT_SECRET=replace_with_strong_secret`

**Dev/mock data**
- A `.env` file (used by Docker Compose) contains `EMS_ADMIN_PASSWORD` which the dev profile uses to seed the admin user for local/dev launches.

**Run locally**
```bash
./mvnw clean spring-boot:run
```

**Run tests (H2)**
```bash
./mvnw clean test
```

**Run with Docker Compose**
```bash
# place .env in repo root with EMS_ADMIN_PASSWORD
docker compose up -d --build
```

## 📑 API Documentation
- OpenAPI JSON: `/api/docs`
- Swagger UI: `/api/ui` (interactive)

## 📊 Example API Usage
1) Login and obtain a token
```bash
curl -X POST https://ems.axeldiego.dev/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"<admin-password>"}'
```

2) Call a protected endpoint
```bash
curl -X GET https://ems.axeldiego.dev/api/employees \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

3) Create an employee (ADMIN only)
```bash
curl -X POST https://ems.axeldiego.dev/api/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com","salary":50000,"department":{"id":1},"status":"ACTIVE"}'
```

## 🧠 Engineering Decisions (brief)
- JWT for stateless, scalable auth and easy microservice integration
- Layered architecture for testability and separation of concerns
- DTOs to protect domain models and evolve API without breaking clients

## 📈 Future Improvements
- Refresh tokens & token rotation
- Rate limiting and WAF rules
- Blue/green or canary deployments
- Full observability (metrics, tracing, logs)

## 📂 CI / CD
- GitHub Actions workflows included: build/test, docker build & push, quality checks (Sonar optional)
- Push to `main` triggers Docker build; tags `v*` create releases

## 🧾 Deployment
- Deployed to a DigitalOcean droplet (Ubuntu + Nginx reverse proxy + systemd service or Docker Compose)
- Ensure Nginx forwards `X-Forwarded-*` headers so Swagger UI generates correct URLs

## 👨‍💻 Author
- **Name:** Axel Diego
- **GitHub:** https://github.com/axelcodeds
