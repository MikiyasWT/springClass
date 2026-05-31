# Clinic Practice — Session Revision Manual

**Project:** clinic-practice  
**Author:** Kai (learning track toward OpenMRS)  
**Date:** 31 May 2026  
**Stack:** Spring Boot 4.x · Java 17 · Spring Data JPA · MySQL (Docker) · Postman

---

## 1. What this project is

A small **REST API** for a fake clinic, separate from `openmrs-dev`. You practice the same backend ideas OpenMRS uses:

```
HTTP request → Controller → Service → Repository → Database
```

| Layer | Your classes | Role |
|-------|----------------|------|
| Web | `PatientController`, `VisitController` | URLs, HTTP status codes, JSON in/out |
| Service | `PatientServiceImpl`, `VisitServiceImpl` | Business rules |
| Repository | `PatientRepository`, `VisitRepository` | Database access (Spring generates SQL) |
| Model | `Patient`, `Visit` | Tables + JSON shape (`@Entity`) |
| Config | `DataInitializer` | Seed data on startup |
| Errors | `GlobalExceptionHandler`, `ApiError` | Central 400 responses |

---

## 2. Sessions completed

| Session | Topic | Key skills |
|---------|--------|------------|
| A–B | Spring Boot + `HelloController` | `@SpringBootApplication`, `./mvnw spring-boot:run` |
| C | Patients GET | `@RestController`, `@GetMapping`, service + in-memory `Map` |
| D | POST create | `@PostMapping`, `@RequestBody`, HTTP 201 |
| E | PUT + DELETE | `@PathVariable`, `Optional`, 204 No Content |
| F | `GlobalExceptionHandler` | `@ControllerAdvice`, one place for 400 errors |
| G3 | Validation | `@Valid`, `@NotBlank`, `MethodArgumentNotValidException` |
| G2 | MySQL + JPA | Docker MySQL, `JpaRepository`, `ddl-auto=update` |
| Visits | Second table | `findByPatientId`, FK-style `patientId`, full CRUD |

---

## 3. Architecture diagram

```
Browser / Postman / curl
        │
        ▼
┌───────────────────┐
│  REST Controllers │  /api/patients, /api/visits
└─────────┬─────────┘
          ▼
┌───────────────────┐
│     Services      │  rules: duplicate id, patient must exist
└─────────┬─────────┘
          ▼
┌───────────────────┐
│   Repositories    │  JpaRepository → Hibernate
└─────────┬─────────┘
          ▼
┌───────────────────┐
│  Docker MySQL     │  port 3307 → database clinic_practice
└───────────────────┘
```

---

## 4. API reference

**Base URL:** `http://localhost:8080`

### Patients

| Method | Path | Success | Notes |
|--------|------|---------|-------|
| GET | `/api/patients` | 200 | List all |
| GET | `/api/patients/{id}` | 200 / 404 | One patient |
| POST | `/api/patients` | 201 | Body needs `id`, `givenName`, `familyName` |
| PUT | `/api/patients/{id}` | 200 / 404 | Body: names only (id from URL) |
| DELETE | `/api/patients/{id}` | 204 / 404 | No body |

### Visits

| Method | Path | Success | Notes |
|--------|------|---------|-------|
| GET | `/api/visits` | 200 | List all |
| GET | `/api/visits/patient/{patientId}` | 200 | Visits for one patient |
| GET | `/api/visits/{id}` | 200 / 404 | One visit |
| POST | `/api/visits` | 201 | `patientId` must exist in `patients` |
| PUT | `/api/visits/{id}` | 200 / 404 | Body must include `patientId` |
| DELETE | `/api/visits/{id}` | 204 / 404 | No body |

### Hello

| Method | Path |
|--------|------|
| GET | `/api/hello` |

---

## 5. How to run the project

### Start database (Docker)

```bash
cd clinic-practice
docker compose up -d
docker compose ps    # wait for healthy
```

MySQL is on **host port 3307** (not 3306 — local MariaDB uses 3306).

### Start application

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
./mvnw spring-boot:run
```

Wait for: `Started ClinicPracticeApplication`

### Test database connection

```bash
mysql -h 127.0.0.1 -P 3307 -u clinic -pclinic_pass clinic_practice -e "SHOW TABLES;"
```

### Stop

```bash
# App: Ctrl+C
docker compose stop
```

---

## 6. Configuration (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/clinic_practice
spring.datasource.username=clinic
spring.datasource.password=clinic_pass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

| Setting | Meaning |
|---------|---------|
| `3307` | Docker MySQL port on your machine |
| `ddl-auto=update` | Hibernate creates/updates tables (learning only; OpenMRS uses Liquibase) |
| `show-sql=true` | Print SQL in console while learning |

---

## 7. Important Spring annotations

| Annotation | Where | Meaning |
|------------|--------|---------|
| `@Entity` | Model | This class is a database table |
| `@Id` | Model field | Primary key |
| `@Table(name = "...")` | Model | Table name |
| `@RestController` | Controller | REST API + JSON responses |
| `@RequestMapping("/api/...")` | Controller | Base path |
| `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping` | Method | HTTP verb + path |
| `@PathVariable` | Method param | Bind `{id}` from URL — **required** |
| `@RequestBody` | Method param | JSON → Java object |
| `@Valid` | Method param | Run validation annotations on body |
| `@Service` | Service impl | Spring bean for business logic |
| `@ControllerAdvice` | Exception handler | Global error handling |
| `@NotBlank` | Model field | Validation: not null/empty |

---

## 8. JPA repository methods (memorize these)

| You might write (wrong) | Correct |
|-------------------------|---------|
| `getAll()` | `findAll()` |
| `existById()` | `existsById()` |
| `findOne()` | `findById()` → returns `Optional` |
| `deleteByID()` | `deleteById()` |

Custom query by naming:

```java
List<Visit> findByPatientId(String patientId);
```

---

## 9. Bugs you hit and fixes (revision gold)

### Typos break compile

- `patientRepositroy` vs `patientRepository` — Java names must match exactly
- `JpaRepositroy` → `JpaRepository`
- Missing semicolon in constructor

### `@PathVariable` missing

URL: `/api/visits/v10` but parameter `String id` without `@PathVariable` → `id` is **null** →  
`existsById(null)` → **"The given id must not be null"**

**Fix:** `delete(@PathVariable String id)`

### `@PutMapping` without `/{id}`

`@PutMapping` alone = only `PUT /api/visits`, not `PUT /api/visits/v10` → **405 Method Not Allowed**

**Fix:** `@PutMapping("/{id}")`

### DELETE logic inverted

```java
// WRONG — returns false when visit EXISTS
if (visitRepository.existsById(id)) {
    return false;
}

// CORRECT
if (!visitRepository.existsById(id)) {
    return false;
}
visitRepository.deleteById(id);
return true;
```

### PUT body missing `patientId`

Update calls `patientRepository.existsById(visit.getPatientId())`. If body has no `patientId`, same **"The given id must not be null"** error.

### Wrong MySQL port

`3397` in properties → **Connection refused**. Docker uses **3307**.

### Maven BUILD SUCCESS vs app failed

Maven can print **BUILD SUCCESS** even when the app crashed on startup. Look for `Application run failed` in logs.

---

## 10. OpenMRS comparison (G1)

| Clinic practice (you) | OpenMRS appointments module |
|----------------------|------------------------------|
| `@RestController` | `@Controller` + `@ResponseBody` (older style) |
| `@GetMapping("/{id}")` | `@RequestMapping(method = GET, value="/{uuid}")` |
| Constructor injection | Often `@Autowired` fields |
| Spring Boot embedded Tomcat | WAR on external Tomcat |
| `/api/patients` | `/openmrs/ws/rest/v1/appointments` |
| `ddl-auto=update` | Liquibase XML migrations |
| Direct `Patient` entity in API | Often DTO + Mapper (`AppointmentRequest`) |

**Same idea:** Controller → Service → data. Your app is modern Boot; OpenMRS modules are older style but the layers match.

---

## 11. Postman

Import: `postman/clinic-practice.postman_collection.json`  
Copy-paste bodies: `postman/POSTMAN-TEST-DATA.md`

**Remember:** Browsers only do GET easily. Use Postman or `curl` for POST, PUT, DELETE.

---

## 12. Seed data (`DataInitializer`)

On empty database:

| Type | id | Notes |
|------|-----|-------|
| Patient | `1`, `2` | First / Second Patient |
| Visit | `v1`, `v2` | Both for patient `1` |

Runs only when `count() == 0` for each table.

---

## 13. What to learn next

1. **DTOs** — separate API JSON from `@Entity` (like OpenMRS `AppointmentRequest`)
2. **OpenMRS module** — one REST endpoint in `openmrs-dev` with SDK
3. **`@ManyToOne`** — JPA relationship Visit → Patient
4. **Liquibase** — how OpenMRS manages schema instead of `ddl-auto`
5. **Spring Security** — auth on REST (OpenMRS uses Basic Auth)

---

## 14. Quick commands cheat sheet

```bash
# Docker DB
docker compose up -d
docker compose ps

# Run app
./mvnw spring-boot:run

# Compile only
./mvnw compile

# MySQL shell
mysql -h 127.0.0.1 -P 3307 -u clinic -pclinic_pass clinic_practice

# Example curl
curl http://localhost:8080/api/patients
curl -X POST http://localhost:8080/api/visits \
  -H "Content-Type: application/json" \
  -d '{"id":"v10","patientId":"1","visitDate":"2026-05-31","reason":"Checkup"}'
```

---

*End of revision manual — clinic-practice learning project.*
