# 🧩 Introduction to Spring Boot

## 🎯 Goal

This project is a first contact with Spring Boot and REST API development.
It builds a minimal yet functional API that sends and receives JSON data, following clean coding and testing best practices.

---

## ⚙️ Technologies

- **Java 21**
- **Spring Boot** (Web, DevTools)
- **Maven**
- **JUnit 5 + MockMvc**
- **Postman** (for manual testing)

---

## 🧱 Project Structure

```text
/src/main/java/cat/itacademy/s04/t01/userapi
 ├── controllers/
 │    ├── HealthController.java
 │    └── UserController.java
 ├── models/
 │    └── User.java
 └── UserApiApplication.java
```

---

## 🧩 Main Endpoints

| Method | Route            | Description                              |
|--------|------------------|------------------------------------------|
| GET    | `/health`        | Checks server status                     |
| GET    | `/users`         | Returns all users (or filters by name)   |
| POST   | `/users`         | Creates a new user                       |
| GET    | `/users/{id}`    | Returns a user by ID                     |

---

## ▶️ How to Run

### Option 1: Run from IDE
Run the `UserApiApplication` class.

### Option 2: Run from terminal (dev mode)

```bash
mvn spring-boot:run
```

### Option 3: Package and run the JAR

```bash
mvn clean package
java -jar target/userapi-0.0.1-SNAPSHOT.jar
```

---

## 🧪 Automated Tests

The classes `HealthControllerTest` and `UserControllerTest` include tests to verify:

- `GET /health` returns
  ```json
  { "status": "OK" }
  ```

- `GET /users` correctly manages the user list and query parameters

- `GET /users/{id}` returns:
  - the correct user when it exists
  - `404 Not Found` if it doesn’t

Run all tests with:

```bash
mvn test
```

---