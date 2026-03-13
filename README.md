# Student Manager

A RESTful backend service for managing student records, built with Spring Boot.

---

## Tech Stack

- Java 17
- Spring Boot 3.4
- Spring Data JPA
- MySQL
- Lombok
- Maven

---

## Getting Started

1. Clone the repository
2. Create a MySQL database named `student_manager`
3. Create `src/main/resources/application-local.properties`:
```properties
   spring.datasource.username=your_username
spring.datasource.password=your_password
```
4. Run `StudentManagerApplication.java`

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /students | Get all students (paginated) |
| GET | /students/{id} | Get student by ID |
| POST | /students | Add a new student |
| PUT | /students/{id}/score | Update student score |
| DELETE | /students | Remove students below score threshold |
| GET | /students/search?name= | Search students by name |
| GET | /students/score?maxScore= | Filter students by max score |
| GET | /students/top?n= | Get top N students by score |
| GET | /students/count?min=&max= | Count students in score range |