# Techeazy Consulting Secure API

## Overview

This project is a Spring Boot application that demonstrates basic CRUD operations with student and subject entities, JWT-based authentication, and role-based access control. It uses an in-memory H2 database for development and testing.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 19 or later
- Apache Maven
- Git

### Clone the Repository

```bash
git clone https://github.com/enjoyimkrsnna/techeazySecureAPI.git
cd techeazySecureAPI
```

### Build and Run the Application

1. **Build the Project**

   ```bash
   mvn clean install
   ```

2. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

   The application will start on port `8000` by default.

## Endpoints

### Authentication

#### Register User

**Endpoint**: `POST /api/v1/auth/register/{username}/{password}`

**Description**: Registers a new user with a specified role.

- **URL Example**: `http://localhost:8000/api/v1/auth/register/{username}/{password}`

- **Response**:
  ```json
  {
    "username": "string",
    "password": "string",
    "authorities": [
      {
        "roleId": "integer",
        "authority": "string"
      }
    ],
    "enabled": "boolean",
    "credentialsNonExpired": "boolean",
    "accountNonExpired": "boolean",
    "accountNonLocked": "boolean"
  }
  ```

#### Login User

**Endpoint**: `POST /api/v1/auth/login/{username}/{password}`

**Description**: Authenticates a user and returns a JWT token.

- **URL Example**: `http://localhost:8000/api/v1/auth/login/{username}/{password}`

- **Response**:
  ```json
  "JWT_Token_String"
  ```

### Students

#### Create Student

**Endpoint**: `POST /api/v1/students`

**Description**: Creates a new student.

- **Request Body**:
  ```json
  {
    "name": "string",
    "address": "string"
  }
  ```

- **Response**:
  ```json
  {
    "id": "integer",
    "name": "string",
    "address": "string",
    "subjects": ["string"]
  }
  ```

#### Get All Students

**Endpoint**: `GET /api/v1/students`

**Description**: Retrieves a list of all students.

- **Response**:
  ```json
  [
    {
      "id": "integer",
      "name": "string",
      "address": "string",
      "subjects": ["string"]
    }
  ]
  ```

### Subjects

#### Create Subject

**Endpoint**: `POST /api/v1/subjects`

**Description**: Creates a new subject.

- **Request Body**:
  ```json
  {
    "name": "string"
  }
  ```

- **Response**:
  ```json
  {
    "id": "integer",
    "name": "string"
  }
  ```

#### Get All Subjects

**Endpoint**: `GET /api/v1/subjects`

**Description**: Retrieves a list of all subjects.

- **Response**:
  ```json
  [
    {
      "id": "integer",
      "name": "string"
    }
  ]
  ```

#### Enroll Student in Subject

**Endpoint**: `POST /api/v1/students/enroll`

**Description**: Enrolls a student in a subject.

- **Parameters**:
    - `studentId` (required): The ID of the student to be enrolled.
    - `subjectName` (required): The name of the subject to enroll the student in.

- **Request Example**:
  ```http
  POST http://localhost:8000/api/v1/students/enroll?studentId={studentId}&subjectName={subjectName}
  ```

- **Response**:
  ```json
  {
    "id": "integer",
    "name": "string",
    "address": "string",
    "subjects": ["string"]
  }
  ```
  

