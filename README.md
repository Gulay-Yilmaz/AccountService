# AccountService - Digital Wallet & Banking Backend

A high-performance, enterprise-grade **Digital Wallet & Account Management Service** built with Java and Spring Boot. This project demonstrates modern backend architecture patterns, focusing on data consistency, performance, and scalability in banking systems.

---

## Features

* **Transactional Money Transfer:** Secure and atomic money transfers between accounts using `@Transactional` to ensure data integrity.
* **Multi-language Support (i18n):** Dynamic error and success messages based on the `Accept-Language` header (supports English and Turkish).
* **Global Exception Handling:** A centralized error management system using `@ControllerAdvice` to provide standardized API responses.
* **Enterprise Architecture:** Implementation of DTO (Data Transfer Object) patterns and layered architecture (Controller -> Service -> Repository).
* **Containerized Infrastructure:** Ready-to-use Docker environment for PostgreSQL and Redis.

---

## Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Database:** PostgreSQL
* **Caching:** Redis (In-progress)
* **Messaging:** Kafka (Planned)
* **Other:** Docker & Docker Compose, Lombok, Spring Data JPA, Maven.

---

## Project Structure

```text
com.example.accountservice
├── config         # Infrastructure & Locale configurations
├── controller     # REST Endpoints
├── dto            # Data Transfer Objects (Request/Response)
├── exception      # Global Exception Handler & Custom Errors
├── model          # Database Entities
├── repository     # Spring Data JPA Repositories
└── service        # Business Logic layer
