# 📌 Employee Management System API

## 🚀 Overview
A production-oriented backend for managing employees and departments with secure access control, validation, and clean API design. The system focuses on scalable patterns (layered architecture, DTO separation) and real-world concerns like authentication, authorization, auditing, and API documentation.

## 🎥 Demo
- **YouTube demo:** [![EMS API Demo](https://img.youtube.com/vi/VIDEO_ID/0.jpg)](https://www.youtube.com/watch?v=VIDEO_ID)
- **Live API URL:** https://your-live-api.example.com

## ✨ Features
- JWT-based authentication with stateless security
- Role-based access control (ADMIN / USER)
- Pagination, sorting, and filtering for employees
- Clean REST API design with DTO validation
- Swagger/OpenAPI documentation for quick testing
- Audit fields for created/updated timestamps

## 🏗 Architecture
- **Layered design:** Controller → Service → Repository
- **DTOs:** Isolate API contracts from persistence models
- **Separation of concerns:** Controllers for HTTP, Services for business rules, Repositories for data access

## 🔐 Security Highlights
- Login returns a JWT token; clients send it via `Authorization: Bearer <token>`
- Public endpoints: `/api/auth/**`, `/api/health`
- Protected endpoints require authentication
- Write operations restricted to `ADMIN` using `@PreAuthorize`
- Passwords are hashed with BCrypt

## 🛠 Tech Stack
**Backend**
- Java 21, Spring Boot, Spring WebMVC
- Spring Security + JWT (jjwt)
- Spring Data JPA

**Database**
- PostgreSQL (production)
- H2 (tests)

**Tools**
- Lombok, Bean Validation
- Springdoc OpenAPI / Swagger UI

## 📦 Installation & Setup
**Prerequisites**
- Java 21
- PostgreSQL

**Environment variables**
- `SPRING_DATASOURCE_URL` (default: `jdbc:postgresql://localhost:5432/ems`)
- `SPRING_DATASOURCE_USERNAME` (default: `postgres`)
- `SPRING_DATASOURCE_PASSWORD` (default: `postgres`)
- `SPRING_JPA_HIBERNATE_DDL_AUTO` (default: `update`)
- `JWT_SECRET` (optional override)

**Run locally**
```bash
./mvnw clean spring-boot:run
```

**Run tests (H2)**
```bash
./mvnw clean test
```

## 📑 API Documentation
- OpenAPI JSON: `/api/docs`
- Swagger UI: `/api/ui`

## 📊 Example API Usage
**Login and get a token**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123"}'
```

**Create an employee (ADMIN only)**
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "firstName":"John",
    "lastName":"Doe",
    "email":"john.doe@example.com",
    "salary":50000,
    "department":{"id":1,"name":"Engineering"},
    "status":"ACTIVE"
  }'
```

**List employees with pagination/filtering**
```bash
curl "http://localhost:8080/api/employees?page=0&size=10&sortBy=id&sortDir=asc&department=Engineering" \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

## 🧠 Engineering Decisions
- **JWT** enables stateless authentication and horizontal scalability.
- **Layered architecture** keeps business logic isolated and testable.
- **DTOs** protect domain models and enforce clean API contracts.

## 📈 Future Improvements
- Refresh tokens and token rotation
- Rate limiting and abuse protection
- Dockerized production deployment
- CI/CD pipelines with automated quality gates

## 👨‍💻 Author
- **Name:** Your Name
- **GitHub:** https://github.com/your-username
- **LinkedIn:** https://linkedin.com/in/your-profile

