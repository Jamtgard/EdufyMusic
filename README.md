# EdufyMusic
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)

## 🎬 Overview
EdufyMusic manages songs and albums within the Edufy platform.  
The service can retrieve music, create new content, and operates as part of a larger microservice ecosystem communicating via Docker Compose.  
Other related services are linked below.

---

## 🧩 Related projects

### Organization
- [EdufyProjects](https://github.com/EudfyProjects)

### Connections
- [Edufy-infra](https://github.com/EudfyProjects/Edufy-infra) – Docker-compose + init.db
- [EdufyEurekaServer](https://github.com/Sommar-skog/EdufyEurekaServer) – Service discovery
- [Gateway](https://github.com/SaraSnail/EdufyGateway) – Entry point for all requests
- [EdufyUser](https://github.com/Jamtgard/EdufyUser) – User handling service
- [EdufyKeycloak](https://github.com/Sommar-skog/EdufyKeycloak) – Keycloak pipeline for auth

### Media connections
- [EdufyCreator](https://github.com/Sommar-skog/EdufyCreator) – Creators
- [EdufyGenre](https://github.com/a-westerberg/EdufyGenre) – Genres
- [EdufyThumb](https://github.com/a-westerberg/EdufyThumb) – Thumbs up/down records
- [EdufyUtility](https://github.com/a-westerberg/EdufyUtility) – Placeholder for algorithms

### Other Media services
- [EdufyVideo](https://github.com/Sommar-skog/EdufyVideo)
- [EdufyPod](https://github.com/SaraSnail/EdufyPod)

---

## 🚀 Tech Stack

- **Language:** Java 21
- **Build Tool:** Maven
- **Framework:** Spring Boot 3.5.7
    - Spring Web
    - Spring Data JPA
    - Spring Security
    - Eureka Client
    - Spring Cloud Loadbalancer
- **Databases:**
    - MySQL 8.0 (Docker)
    - H2 (Development)
- **Security:**
    - OAuth2 Resource Server
- **Testing:**
    - Mockito
    - JUnit 5


---

## 🏁 Getting Started

### Prerequisites
- Java 21
- Maven
- Docker
- Postman
- Keycloak

---

### 🔌 Ports

#### Connections
- **Eureka:** `8761`
- **Gateway:** `4545`
- **MySQL:** `3307`
- **User:** `8686`
- **Keycloak:** `8080`

#### Media connections
- **Creator:** `8787`
- **Genre:** `8585`
- **Thumb:** `8484`
- **Utility:** `8888`

#### Media services
- **Video:** `8383`
- **Music:** `8181`
- **Pod:** `8282`

---

## 🔒 Authentication & Roles

EdufyMusic uses **OAuth2 + Keycloak** for authentication and authorization.

### User Roles
- **edufy_realm_admin** – Admin access across all microservices
- **music_admin** – Create and manage music content
- **music_user** – Retrieve songs & albums
- **microservice_access** – Internal service-to-service communication

>_Note: These are not "real" users/admin. They are placeholders for production and used under development._


| Role                | Username            | Password |
|---------------------|---------------------|----------|
| music_admin         | music_admin         | admin    |
| edufy_realm_admin   | edufy_realm_admin   | admin    |
| music_user          | music_user          | music    |
| microservice_access | –                   | –        |

> Note: Unauthenticated requests will receive a `401 Unauthorized` response.

> `microservice_access` is a role that clients uses between each other to authorize access


---

## 📚 API Endpoints

### Admin – Roles `music_admin` & `edufy_realm_admin`
* **POST** `/music/add-song` – Create a new song (and connect it to album/genre/creator records)
* **POST** `/music/add-album` – Create a new album (with songs + external records)
* **GET** `/music/song/{id}` – Get song by ID
* **GET** `/music/album/{id}` – Get album by ID

---

### Client – Role `microservice_access`
* **GET** `/music/user-history/{userId}` – Get user's song history (song IDs as DTOs)
* **GET** `/music/songs-genre/{genreId}` – Get songs by genre (used for service-to-service calls). Intended to be used in larger flow of recommending music to users based on most played genre.

---

### Common – Any authenticated user
* **GET** `/music/songs-all` – List all songs (filters inactive content for non-admins)
* **GET** `/music/albums-all` – List all albums (filters inactive content for non-admins)
* **GET** `/music/discography/{creatorId}` – Get all songs/albums by a creator (via Creator service)


---

### User – Role `music_user`
* **GET** `/music/song/search` – Search songs by title (`?title=...`)
* **GET** `/music/album/search` – Search albums by title (`?title=...`)
* **GET** `/music/play/{songId}` – "Play" song, returns song URL and updates user history

---

## 🐳 Docker
- This service runs via `docker-compose.yml` found in **Edufy-infra**.
- Docker network: `edufy-network`.

---

## 🛢️ MySQL Database

| Name           | User | Pass | Database |
|----------------|------|------|----------|
| edufy_mysql    | assa | assa | main     |
| edufy_music_db | assa | assa | music    |

- **Version:** 8.0
- **SQL files:**
    - Global init file is located in Edufy-infra
    - Music service uses `Data.sql` (dev) + `import.sql` (prod)
- **Default port:** `3306` → mapped as `3307:3306`

- **Connection Example :**
  ```
    spring.datasource.url=jdbc:mysql://edufy-mysql:3306/edufy_music_db
    spring.datasource.username=assa
    spring.datasource.password=assa
    spring.jpa.hibernate.ddl-auto=update
  ```

> _README made by [Jamtgard](https://github.com/Jamtgard)_



