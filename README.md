# Personal Finance Management System

A RESTful API built with Spring Boot for managing personal finances, including transactions, budgets, savings goals, and recurring payments.

## Architecture Diagram

<img width="3840" height="1755" alt="Untitled diagram _ Mermaid Chart-2025-09-08-234100" src="https://github.com/user-attachments/assets/5de8a5aa-1946-4f65-8845-a1cd1ada462a" />

## Tech Stack

- **Java 21**
- **Spring Boot 3.4.4** (Web, Data JPA, Security, Validation)
- **MySQL** – relational database
- **JWT (jjwt 0.11.5)** – stateless authentication
- **Maven** – build & dependency management

## Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8.0+ 

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Ranelan/PersonalFinance.git
cd PersonalFinance
```

### 2. Create the database

```sql
CREATE DATABASE personalFinancedb;
```

### 3. Configure the application

Edit `src/main/resources/application.properties` and set your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/personalFinancedb
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```

### 4. Build and run

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8081/personalFinance`.

### 5. Swagger UI

Interactive API documentation is available at:

```
http://localhost:8081/personalFinance/swagger-ui/index.html
```

## API Reference

All endpoints are prefixed with `/personalFinance`.

### Regular Users — `/api/regularUser`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Register a new user |
| POST | `/login` | Authenticate and receive a JWT |
| GET | `/read/{id}` | Get user by ID |
| PUT | `/update` | Update user details |
| DELETE | `/delete/{id}` | Delete user |
| GET | `/findByUserName/{userName}` | Find user by username |
| GET | `/findByEmail/{email}` | Find user by email |
| GET | `/findAll` | List all users |

### Transactions — `/api/transactions`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create a transaction |
| GET | `/read/{id}` | Get transaction by ID |
| PUT | `/update` | Update a transaction |
| DELETE | `/delete/{id}` | Delete a transaction |
| GET | `/all` | List all transactions |
| GET | `/byUser/{userId}` | List transactions for a user |

### Budgets — `/api/budget`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create a budget |
| GET | `/read/{id}` | Get budget by ID |
| PUT | `/update` | Update a budget |
| DELETE | `/delete/{id}` | Delete a budget |
| GET | `/findAll` | List all budgets |
| GET | `/byUser/{userId}` | List budgets for a user |

### Goals — `/api/goal`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create a savings goal |
| GET | `/read/{goalId}` | Get goal by ID |
| PUT | `/update` | Update a goal |
| DELETE | `/delete/{goalId}` | Delete a goal |
| GET | `/findByGoalName/{goalName}` | Find goal by name |
| GET | `/findByDeadLine/{deadLine}` | Find goals by deadline |
| GET | `/findAll` | List all goals |
| GET | `/byUser/{userId}` | List goals for a user |

### Categories — `/api/category`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create a category |
| GET | `/read/{id}` | Get category by ID |
| PUT | `/update` | Update a category |
| DELETE | `/delete/{id}` | Delete a category |
| GET | `/findByName/{name}` | Find category by name |
| GET | `/findByType/{type}` | Find categories by type |
| GET | `/findAll` | List all categories |
| GET | `/byUser/{userId}` | List categories for a user |

### Recurring Transactions — `/api/recurringTransactions`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create a recurring transaction |
| GET | `/read/{id}` | Get recurring transaction by ID |
| PUT | `/update` | Update a recurring transaction |
| DELETE | `/delete/{id}` | Delete a recurring transaction |
| GET | `/findByRecurrenceType/{type}` | Find by recurrence type |
| GET | `/findByNextExecution/{nextExecution}` | Find by next execution date |
| GET | `/findAll` | List all recurring transactions |
| GET | `/byCategory/{categoryId}` | List by category |
| GET | `/byUser/{userId}` | List by user |

### Admin — `/api/admin`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create an admin account |
| POST | `/login` | Admin authentication |
| GET | `/read/{id}` | Get admin by ID |
| PUT | `/update` | Update admin details |
| DELETE | `/delete/{id}` | Delete admin |
| GET | `/findByUserName/{userName}` | Find admin by username |
| GET | `/findByEmail/{email}` | Find admin by email |
| GET | `/findAll` | List all admins |
| GET | `/regular-users` | List all regular users |
| DELETE | `/regular-users/delete/{id}` | Remove a regular user |
| PUT | `/regular-users/update/{id}` | Update a regular user |
| POST | `/categories/create` | Create a category (admin) |
| PUT | `/categories/update/{id}` | Update a category (admin) |
| DELETE | `/categories/delete/{id}` | Delete a category (admin) |
| GET | `/categories/all` | List all categories (admin) |
| GET | `/analytics` | System-wide financial analytics |
| GET | `/analytics/category/{category}` | Analytics by category |
| GET | `/analytics/date-range` | Analytics for a date range |
| GET | `/analytics/transaction-type/{transactionType}` | Analytics by transaction type |

## Project Structure

```
src/
├── main/
│   ├── java/za/ac/cput/
│   │   ├── Main.java                   # Application entry point
│   │   ├── domain/                     # Entity classes (User, Transaction, Budget, Goal, …)
│   │   ├── repository/                 # Spring Data JPA repositories
│   │   ├── service/                    # Business logic services
│   │   └── controller/                 # REST controllers
│   └── resources/
│       └── application.properties      # App configuration
└── test/                               # Unit and integration tests
```


