# 🛍️ Online Store — REST API

A production-ready **Spring Boot** e-commerce REST API featuring JWT-based authentication, refresh token rotation, role-based access control, and a full product and cart management system.

---

## 📑 Table of Contents

- [Tech Stack](#-tech-stack)
- [Architecture Overview](#-architecture-overview)
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [API Reference](#-api-reference)
- [Security Model](#-security-model)
- [Database Schema](#-database-schema)
- [Error Handling](#-error-handling)

---

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot  |
| Security | Spring Security, JWT |
| Persistence | Spring Data JPA, Hibernate, PostgreSQL |
| Build Tool | Gradle |
| Mapping | MapStruct |
| Boilerplate Reduction | Lombok |
| Validation | Jakarta Validation |
| Profiles | `dev` / `prod` via Spring Profiles |

---

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────┐
│                  Client                     │
└───────────────────┬─────────────────────────┘
                    │ HTTP
┌───────────────────▼─────────────────────────┐
│   JwtAuthFilter (OncePerRequestFilter)      │
│   Extracts & validates Bearer token         │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│           SecurityFilterChain               │
│   Route-level authorization + Entry Point   │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│               Controllers                   │
│  AuthController │ ProductController         │
│  ItemCartController │ CreateAdminController │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│                Services                     │
│  AuthService │ ProductService               │
│  ItemCartService │ RefreshTokenService      │
│  JwtService  │ CreateAdminService           │
└───────────────────┬─────────────────────────┘
                    │
┌───────────────────▼─────────────────────────┐
│              Repositories (JPA)             │
│  UserRepository │ ProductRepository         │
│ RefreshTokenRepository │ ItemCartRepository │
└───────────────────┬─────────────────────────┘
                    │
                PostgreSQL
```

---

## ✨ Features

- **JWT Authentication** — Stateless access tokens with configurable expiry
- **Refresh Token Rotation** — Single-use refresh tokens with automatic rotation on every use
- **Role-Based Access Control** — Three-tier role system: `USER`, `ADMIN`, `SUPER_ADMIN`
- **Super Admin Seeding** — First super admin is automatically created on startup via `ApplicationRunner`
- **Product Management** — Full CRUD with variant (color/size/stock) and image support
- **Shopping Cart** — Per-user cart with quantity accumulation on duplicate items
- **Global Exception Handling** — Structured JSON error responses for all exception types
- **Optimistic Locking** — `@Version` on `User` and `Product` entities to prevent lost updates
- **Profile-Based Config** — Clean separation between `dev` and `prod` environments
- **Custom Auth Entry Point** — JSON 401 responses instead of Spring's default HTML error page

---

## 📁 Project Structure

```
src/main/java/com/na/store/
│
├── configs/
│   ├── SecurityConfig.java              # Filter chain, auth provider, beans
│   ├── JwtAuthFilter.java               # JWT extraction & validation filter
│   ├── CustomAuthenticationEntryPoint.java  # JSON 401 responses
│   └── SuperAdminSeeder.java            # Startup data seeder
│
├── controllers/
│   ├── AuthController.java              # /api/auth/**
│   ├── ProductController.java           # /api/products/**
│   ├── ItemCartController.java          # /api/cart/**
│   └── CreateAdminController.java       # /api/admin/**
│
├── services/
│   ├── AuthService.java
│   ├── JwtService.java
│   ├── RefreshTokenService.java
│   ├── ProductService.java
│   ├── ItemCartService.java
│   ├── CreateAdminService.java
│   └── CustomUserDetailsService.java
│
├── entities/
│   ├── User.java
│   ├── RefreshToken.java
│   ├── Product.java
│   ├── ProductVariant.java
│   ├── ProductImages.java
│   └── ItemCart.java
│
├── repositories/
│   ├── UserRepository.java
│   ├── RefreshTokenRepository.java
│   ├── ProductRepository.java
│   └── ItemCartRepository.java
│
├── dtos/
│   ├── user/                            # UserRegisterRequest, UserLoginRequest, etc.
│   ├── product/                         # ProductRequest, ProductResponse, etc.
│   ├── cart/                            # ItemCartRequest, ItemCartResponse
│   └── reftoken/                        # RefreshTokenRequest, RefreshTokenResponse
│
├── mappers/                             # MapStruct interfaces
├── enums/                               # UserRole, ClotheCategory, ClotheSize
└── exceptions/                          # Custom exceptions + GlobalExceptionHandler
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- PostgreSQL 14+
- Gradle 8+

### 1. Clone the repository

```bash
git clone https://github.com/zidi-abderrahmen/online-shop-backend.git
cd online-shop-backend
```

### 2. Create the database

```sql
CREATE DATABASE your_db_name;
```

### 3. Configure the dev profile

Create `src/main/resources/application-dev.yaml` with your local PostgreSQL credentials (see [Configuration](#configuration)).

### 4. Run the application

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

The API will be available at `http://localhost:8080`.

---

## ⚙️ Configuration

### `application-dev.yaml`

```yaml
application:
  security:
    jwt:
      secret: <base64-encoded-256-bit-secret>
      expiration: 900000             # 15 minutes (ms)
      refresh-expiration: 604800000  # 7 days (ms)

  super_admin:
    full_name: Your Name
    email: admin@example.com
    password: StrongPassword1

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db_name
    username: your_db_user
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
```

### `application-prod.yaml`

All sensitive values are externalized to environment variables in production:

| Environment Variable | Description |
|---|---|
| `JWT_SECRET` | Base64-encoded HMAC-SHA256 secret |
| `JWT_EXPIRATION` | Access token TTL in milliseconds |
| `JWT_REFRESH_EXPIRATION` | Refresh token TTL in milliseconds |
| `DATABASE_URL` | Full JDBC connection URL |
| `DATABASE_USERNAME` | Database username |
| `DATABASE_PASSWORD` | Database password |
| `SUPER_ADMIN_FULL_NAME` | Seeded super admin display name |
| `SUPER_ADMIN_EMAIL` | Seeded super admin email |
| `SUPER_ADMIN_PASSWORD` | Seeded super admin password |

---

## 📡 API Reference

### Authentication — `/api/auth`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | Public | Register a new user |
| `POST` | `/api/auth/login` | Public | Login and receive tokens |
| `POST` | `/api/auth/refresh-token` | Public | Rotate refresh token |
| `POST` | `/api/auth/logout` | Public | Invalidate refresh token |

#### Register — `POST /api/auth/register`

**Request body:**
```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "Secret123",
  "confirmPassword": "Secret123"
}
```

**Response `201`:**
```json
{
  "id": "uuid",
  "name": "John Doe",
  "email": "john@example.com"
}
```

#### Login — `POST /api/auth/login`

**Request body:**
```json
{
  "email": "john@example.com",
  "password": "Secret123"
}
```

**Response `200`:**
```json
{
  "accessToken": "<jwt>",
  "refreshToken": "<uuid>",
  "user": {
    "id": "uuid",
    "name": "John Doe",
    "email": "john@example.com"
  }
}
```

#### Refresh Token — `POST /api/auth/refresh-token`

**Request body:**
```json
{
  "token": "<refresh-token-uuid>"
}
```

**Response `200`:**
```json
{
  "accessToken": "<new-jwt>",
  "refreshToken": "<new-refresh-token-uuid>"
}
```

---

### Products — `/api/products`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/products` | Public | Get all products |
| `GET` | `/api/products/id/{id}` | Public | Get product by ID |
| `POST` | `/api/products` | `ADMIN` / `SUPER_ADMIN` | Create a product |
| `PUT` | `/api/products/id/{id}` | `ADMIN` / `SUPER_ADMIN` | Update a product |
| `DELETE` | `/api/products/id/{id}` | `ADMIN` / `SUPER_ADMIN` | Delete a product |

#### Create Product — `POST /api/products`

**Request body:**
```json
{
  "name": "Classic White Tee",
  "description": "100% cotton classic fit",
  "price": 29.99,
  "category": "TOP",
  "imagesUrl": [
    { "url": "https://cdn.example.com/img1.jpg", "altText": "Front view" }
  ],
  "variants": [
    { "color": "White", "stock": 100, "sizes": ["S", "M", "L", "XL"] }
  ]
}
```

---

### Shopping Cart — `/api/cart`

> All cart endpoints require `USER` role.

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/cart` | Get current user's cart |
| `POST` | `/api/cart` | Add item (accumulates quantity if exists) |
| `DELETE` | `/api/cart/id/{id}` | Remove item from cart |

#### Add to Cart — `POST /api/cart`

```json
{
  "productId": 1,
  "quantity": 2
}
```

---

### Admin Management — `/api/admin`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/admin/register` | `SUPER_ADMIN` | Create a new admin account |

---

## 🔐 Security Model

### Roles

| Role | Capabilities |
|---|---|
| `USER` | Browse products, manage own cart |
| `ADMIN` | `USER` + Create / Update / Delete products, Create admins |
| `SUPER_ADMIN` | `ADMIN` + Create admin accounts |

### Token Flow

```
Login ──► Access Token (15 min) + Refresh Token (7 days)
                │
         Access Token expires
                │
         POST /auth/refresh-token
                │
         Old refresh token is deleted ──► New token pair issued
```

- Access tokens are **short-lived JWTs** signed with HMAC-SHA256.
- Refresh tokens are **opaque UUIDs** stored in the database.
- Each refresh is a **one-time use** — the old token is deleted and a new one is issued.
- Logout **immediately invalidates** the refresh token server-side.

### Route-Level Authorization

```
/api/auth/**             → Public
GET /api/products/**     → Public
/api/cart/**             → Authenticated (USER role via @PreAuthorize)
POST/PUT/DELETE /api/products/** → Authenticated (ADMIN/SUPER_ADMIN via @PreAuthorize)
/api/admin/**            → Authenticated (SUPER_ADMIN via @PreAuthorize)
everything else          → Authenticated
```

---

## 🗄️ Database Schema

```
users
  id (UUID PK), name, email (unique), password,
  role (ENUM), version, created_at, updated_at

refresh_tokens
  id (BIGINT PK), token (unique), expiration_date,
  user_id (FK → users)

products
  id (BIGINT PK), name (unique), description, price,
  category (ENUM), version, created_at, updated_at

product_variant
  id (BIGINT PK), color, stock, sizes (ENUM set),
  product_id (FK → products)

product_images
  id (BIGINT PK), url, alt_text,
  product_id (FK → products)

item_cart
  id (BIGINT PK), quantity,
  user_id (FK → users),
  product_id (FK → products)
```

---

## 🚨 Error Handling

All errors return a consistent JSON body like this:

```json
{
  "timestamp": "2025-05-22T12:00:00",
  "message": "Human-readable error message"
}
```

Validation errors return a field-level map like this:

```json
{
  "email": "Email must be valid",
  "password": "Password must be at least 8 characters long"
}
```

| Exception | HTTP Status |
|---|---|
| `AlreadyExistsException` | `400 Bad Request` |
| `PasswordMismatchException` | `400 Bad Request` |
| `NotFoundException` | `404 Not Found` |
| `InvalidException` | `401 Unauthorized` |
| `ExpiredException` | `401 Unauthorized` |
| `MethodArgumentNotValidException` | `400 Bad Request` |
| Missing / invalid JWT | `401 Unauthorized` |

---

## 📄 License

This project is licensed under the MIT License.