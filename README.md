# roombooking

A Spring Boot REST API for managing room bookings, built with **PostgreSQL** and secured using **JWT authentication**.

---

## 🚀 Features
- User authentication & role-based access (`ADMIN`, `USER`)
- Room management (CRUD operations)
- Booking management
- PostgreSQL database integration
- JWT-based authentication
- API documentation via **Swagger** & **Postman**

---

## ⚙️ Tech Stack
- **Java 17+**
- **Spring Boot 3.5.5**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Maven**

---
## 📦 Setup

### 1️⃣ Clone the Repository

git clone https://github.com/your-username/roombooking.git
cd roombooking


### 2️⃣ Configure PostgreSQL

Ensure PostgreSQL is running and create the database:

CREATE DATABASE room_booking_db;


### 3️⃣ Configure Environment Variables

Update `src/main/resources/application.properties` with your local PostgreSQL settings:

spring.datasource.url=jdbc:postgresql://localhost:5433/room_booking_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

### JWT
app.jwt.secret=YOUR_SECRET_KEY
app.jwt.expiration=3600000

springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

server.port:8086


### 4️⃣ Build & Run
mvn clean install
mvn spring-boot:run

---

## API Documentation

You can test the APIs using the provided Postman collection:
👉 [Download Postman Collection](docs/postman/roombooking-collection.json)

---

## 🔑 Seed Users for Testing

The following users are automatically created when the application starts (via `DataSeeder` class):

| Username | Password  | Role  |
|----------|-----------|-------|
| admin    | admin123  | ADMIN |
| user     | user123   | USER  |

👉 Use these credentials to test authentication and role-based access in Postman.

---

## ⚖️ Assumptions & Trade-offs

- The application is intended for **local development and testing**; it is **not production-ready**.
- Seed user passwords in the **DataSeeder class** are stored in **plaintext for demo purposes**. In production, always store passwords **hashed**.
- The app uses **PostgreSQL** running locally on port `5433`. Adjust the port if your setup differs.
- API security is implemented using **JWT** with **stateless sessions**.
- Database schema auto-update (`spring.jpa.hibernate.ddl-auto=update`) is used for convenience in development. In production, use **proper migration tools** like Flyway or Liquibase.
- All sensitive information such as database passwords and JWT secrets should **never be committed to the repository**. Use environment variables or a secure secrets manager instead.




