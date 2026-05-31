# Clinic Practice — Where We Left Off

**Project:** `/home/kai/Documents/clinic-practice`  
**Learning mode:** You type the code; the assistant guides only (unless you ask to write files).  
**Last updated:** May 2026

---

## Done so far

| Session | Topic | Status |
|---------|--------|--------|
| A–B | Spring Boot skeleton + `HelloController` | Done |
| C | `Patient` model + `PatientService` + GET endpoints | Done |
| D | `POST /api/patients` (create) | Done |
| E | `PUT` + `DELETE` | Done |
| F | `GlobalExceptionHandler` + `ApiError` | Done |
| G3 | Validation (`spring-boot-starter-validation`, `@Valid`) | Done |
| G1 | Compare with OpenMRS `AppointmentsController` | Discussed (read-only) |

**Current data storage:** in-memory `Map` in `PatientServiceImpl` (data is lost when the app restarts).

**Not started:** **G2 — MySQL + JPA** (next session).

---

## API you have today

| Method | Path | Purpose |
|--------|------|---------|
| GET | `/api/hello` | Hello smoke test |
| GET | `/api/patients` | List patients |
| GET | `/api/patients/{id}` | One patient (404 if missing) |
| POST | `/api/patients` | Create (201) |
| PUT | `/api/patients/{id}` | Update (200 / 404) |
| DELETE | `/api/patients/{id}` | Delete (204 / 404) |

**Run the app:**

```bash
cd /home/kai/Documents/clinic-practice
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
./mvnw spring-boot:run
```

---

## G1 reminder — OpenMRS vs your app

| Your clinic-practice | OpenMRS appointments module |
|----------------------|------------------------------|
| `@RestController`, `@GetMapping` | `@Controller`, `@RequestMapping(method=...)` (older style) |
| Constructor injection | Often `@Autowired` fields |
| Spring Boot, embedded Tomcat | WAR on Tomcat |
| `/api/patients` | `/openmrs/ws/rest/v1/appointments` |

**Same pattern:** HTTP → Controller → Service → Data.

**OpenMRS file to compare (read-only):**  
`/home/kai/openmrs-dev/backend/openmrs-module-appointments/omod/src/main/java/org/openmrs/module/appointments/web/controller/AppointmentsController.java`

---

## Next session: G2 — MySQL + JPA (Docker database)

Use the **Docker MySQL** in this project — **not** your local MariaDB on port 3306 and **not** the OpenMRS `openmrs` database.

### Part A — Start Docker MySQL (terminal)

From the project root:

```bash
cd /home/kai/Documents/clinic-practice
docker compose up -d
docker compose ps
```

Wait until `db` is **healthy**. MySQL is exposed on **host port 3307** (container 3306).

Test login:

```bash
mysql -h 127.0.0.1 -P 3307 -u clinic -pclinic_pass clinic_practice -e "SHOW TABLES;"
```

Stop when done practicing:

```bash
docker compose down
```

Data persists in Docker volume `clinic_practice_mysql_data` until you run `docker compose down -v`.

### Part B — Code checklist (you type)

| Step | File | Action |
|------|------|--------|
| 1 | `pom.xml` | Add `spring-boot-starter-data-jpa` and `mysql-connector-j` |
| 2 | `src/main/resources/application.properties` | MySQL + JPA settings (see below) |
| 3 | `model/Patient.java` | Add `@Entity`, `@Table(name = "patients")`, `@Id` on `id` |
| 4 | **New** `repository/PatientRepository.java` | `extends JpaRepository<Patient, String>` |
| 5 | `service/PatientServiceImpl.java` | Replace `Map` with `PatientRepository` |
| 6 | **New** `config/DataInitializer.java` | `CommandLineRunner` — seed patients 1 & 2 if table empty |
| 7 | Restart | `./mvnw spring-boot:run` |

### `application.properties` template

```properties
spring.application.name=clinic-practice

spring.datasource.url=jdbc:mysql://localhost:3307/clinic_practice
spring.datasource.username=clinic
spring.datasource.password=clinic_pass

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### `pom.xml` dependencies to add

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Verify G2 worked

```bash
curl http://localhost:8080/api/patients

mysql -h 127.0.0.1 -P 3307 -u clinic -pclinic_pass clinic_practice -e "SELECT * FROM patients;"
```

1. `GET /api/patients` — should show seeded patients.  
2. `POST` a new patient, restart the app, `GET` again — data should **persist** (unlike the in-memory `Map`).

---

## Small fixes to double-check

- `PatientController.delete` should use `@PathVariable String id`.
- `PatientServiceImpl.delete` should `return false` when the patient does not exist (not delete and return `true`).

---

## How to resume

Open this project in Cursor and say:

> "Starting G2 — MySQL + JPA"

Paste any startup error if something fails.

---

## OpenMRS reference

| Topic | OpenMRS | This project (after G2) |
|-------|---------|-------------------------|
| Database | MySQL / MariaDB | MySQL (`clinic_practice`) |
| ORM | Hibernate | Hibernate via Spring Data JPA |
| Schema changes | Liquibase | `ddl-auto=update` (learning only) |
