# Clinic Practice — Where We Left Off

**Project:** `/home/kai/Documents/clinic-practice`  
**Learning mode:** You type the code; the assistant guides only (unless you ask to write files).  
**Last updated:** 31 May 2026 — end of DTO session (paused for tomorrow)  
**Git branch:** `level02AdvancedSpringPractices`  
**Remote:** https://github.com/MikiyasWT/springClass.git  

---

## Resume tomorrow — say this in Cursor

> **"Resume clinic-practice — explain DTOs and PatientController at code level, then help me test and fix."**

Start the app first:

```bash
cd /home/kai/Documents/clinic-practice
docker compose up -d
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
./mvnw spring-boot:run
```

---

## Where we are in the learning path

| Done | In progress | Not started |
|------|-------------|-------------|
| Spring Boot + CRUD | **DTOs for Patient** (files written, concepts need review) | DTOs for Visit |
| MySQL + JPA | | `@ManyToOne` Visit → Patient |
| Visits CRUD | | OpenMRS module |
| Validation + GlobalExceptionHandler | | Liquibase, Security |

**You typed:** `PatientCreateRequest`, `PatientUpdateRequest`, `PatientResponse`, `PatientMapper`, new `PatientController`.  
**Project compiles** (`./mvnw compile` succeeds).  
**You said:** you don't fully understand DTOs and the controller yet — **tomorrow we explain line-by-line, then test in Postman.**

---

## Part A — The big picture (read this first tomorrow)

### Before DTOs (old way)

```
Postman sends JSON  →  Spring converts JSON to Patient (@Entity)  →  Service  →  Database
Database row        →  Patient (@Entity)  →  Spring converts to JSON  →  Postman
```

**Problem:** The same Java class (`Patient`) was used for **three jobs**:

1. REST API (what Postman sends/receives)
2. JPA/Hibernate (what MySQL stores)
3. Validation (`@NotBlank` on the entity)

That works for learning, but in real apps (and OpenMRS) you **split** API shape from database shape.

### After DTOs (new way)

```
Postman JSON
    ↓
PatientCreateRequest / PatientUpdateRequest / PatientResponse   ← DTOs (API layer only)
    ↓
PatientMapper  (converts DTO ↔ Patient)
    ↓
Patient (@Entity)   ← still used inside Service + Repository
    ↓
MySQL
```

**Rule to remember:**

| Class | Layer | Who uses it |
|-------|-------|-------------|
| `PatientCreateRequest` | Web | Controller on **POST** only |
| `PatientUpdateRequest` | Web | Controller on **PUT** only |
| `PatientResponse` | Web | Controller on **GET / POST / PUT** responses |
| `PatientMapper` | Web | Controller converts between DTO and entity |
| `Patient` | Model + DB | **Service** and **Repository** only (controller should not expose it) |

The **service did not change** — it still takes and returns `Patient`. Only the **controller** changed to speak DTOs to the outside world.

---

## Part B — What is a DTO?

**DTO = Data Transfer Object**

A plain Java class with:

- private fields
- getters and setters
- **no** `@Entity`, **no** `@Table`, **no** database annotations

Its only job: **carry data across the HTTP boundary** (JSON ↔ Java).

Think of it as a **form** the client fills in:

- **Create form** (`PatientCreateRequest`) — includes `id` because the client chooses the id on POST
- **Update form** (`PatientUpdateRequest`) — **no** `id` because the id is already in the URL (`PUT /api/patients/99`)
- **Receipt** (`PatientResponse`) — what the server sends back after any read or write

---

## Part C — Your files explained (code level)

### 1. `PatientCreateRequest.java` — POST body

**File:** `src/main/java/.../web/dto/PatientCreateRequest.java`

```java
@NotBlank(message = "id is required")
private String id;

@NotBlank(message = "givenName is required")
private String givenName;

@NotBlank(message = "familyName is required")
private String familyName;
```

**Line by line:**

| Code | Meaning |
|------|---------|
| `@NotBlank` | Jakarta Validation: field cannot be null, empty, or only spaces. Runs when controller has `@Valid`. |
| `private String id` | Client must send `"id"` in JSON on create. |
| `public PatientCreateRequest() {}` | **Empty constructor** — Jackson/Spring creates this object from JSON, then calls setters. **Required for POST.** |
| `getId()` / `setId()` | Jackson reads/writes JSON field `"id"`. |

**Example JSON from Postman:**

```json
{
  "id": "99",
  "givenName": "Test",
  "familyName": "User"
}
```

Spring does roughly:

```java
PatientCreateRequest request = new PatientCreateRequest();
request.setId("99");
request.setGivenName("Test");
request.setFamilyName("User");
```

Then `@Valid` checks all `@NotBlank` fields. If `givenName` is missing → `MethodArgumentNotValidException` → your `GlobalExceptionHandler` returns 400.

**Small fix in your file:** make `getFamilyName()` **public** (you have it package-private). Jackson prefers public getters.

---

### 2. `PatientUpdateRequest.java` — PUT body

```java
@NotBlank(message = "givenName can't be empty")
private String givenName;

@NotBlank(message = "familyName can't be empty")
private String familyName;
// NO id field here
```

**Why no `id`?**

PUT URL is: `PUT /api/patients/99`

The `99` comes from `@PathVariable String id` in the controller — **not** from the JSON body.

So the update body is only:

```json
{
  "givenName": "Updated",
  "familyName": "Name"
}
```

If you put `id` in the body, the controller **ignores** it for mapping — `PatientMapper.toEntity(id, request)` uses the **path** id.

---

### 3. `PatientResponse.java` — what the API returns

```java
private String id;
private String givenName;
private String familyName;
// getters + setters
```

**Purpose:** Same fields as today for the client, but this is **not** an entity — it's safe to add/remove API fields later without changing the database table.

**When it's used:** Every time the controller returns patient data to Postman (list, one, create, update).

**Bug to fix tomorrow:** your `PatientResponse` is **missing `getId()` and `setId()`**.  
Without `getId()`, Jackson will **not** include `"id"` in JSON responses. Add:

```java
public String getId() {
    return id;
}

public void setId(String id) {
    this.id = id;
}
```

---

### 4. `PatientMapper.java` — the translator

```java
public static Patient toEntity(PatientCreateRequest request) {
    return new Patient(
        request.getId(),
        request.getGivenName(),
        request.getFamilyName()
    );
}

public static Patient toEntity(String id, PatientUpdateRequest request) {
    return new Patient(
        id,                              // from URL path
        request.getGivenName(),
        request.getFamilyName()
    );
}

public static PatientResponse toResponse(Patient patient) {
    return new PatientResponse(
        patient.getId(),
        patient.getGivenName(),
        patient.getFamilyName()
    );
}
```

**Why three methods?**

| Method | When | Input | Output |
|--------|------|-------|--------|
| `toEntity(PatientCreateRequest)` | POST | Body has id + names | `Patient` for service |
| `toEntity(String id, PatientUpdateRequest)` | PUT | Path has id, body has names | `Patient` for service |
| `toResponse(Patient)` | GET / POST / PUT response | Entity from DB | `PatientResponse` for JSON |

**Why `private PatientMapper() {}`?**  
Utility class — you only call `PatientMapper.toEntity(...)`, you never `new PatientMapper()`.

**Why static?**  
No state needed; just pure conversion functions.

---

### 5. `PatientController.java` — endpoint by endpoint

Your new controller (old version is commented at top of file — good for comparison).

#### Class setup

```java
@RestController
@RequestMapping("api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
```

- `@RestController` — every method return value becomes JSON (not HTML).
- `@RequestMapping("api/patients")` — base path. Prefer `"/api/patients"` (leading slash) for consistency.
- Constructor injection — Spring creates `PatientServiceImpl` and passes it in.

---

#### GET `/api/patients` — list

```java
@GetMapping
public List<PatientResponse> list() {
    return patientService.getAll()
            .stream()
            .map(PatientMapper::toResponse)
            .toList();
}
```

**Step by step:**

1. `patientService.getAll()` → `List<Patient>` from database (entities).
2. `.stream()` → process each patient one by one.
3. `.map(PatientMapper::toResponse)` → convert each `Patient` → `PatientResponse`.
4. `.toList()` → collect into `List<PatientResponse>`.
5. Spring converts list to JSON array.

**Before DTOs:** returned `List<Patient>` (entity leaked to API).  
**After DTOs:** returns `List<PatientResponse>` (API shape only).

---

#### GET `/api/patients/{id}` — one patient

```java
@GetMapping("/{id}")
public ResponseEntity<PatientResponse> one(@PathVariable String id) {
    return patientService.getById(id)
            .map(PatientMapper::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
```

**Step by step:**

1. `@PathVariable String id` — take `99` from URL `/api/patients/99`.
2. `getById(id)` → `Optional<Patient>` (empty if not found).
3. If present: `map(PatientMapper::toResponse)` → `Optional<PatientResponse>`.
4. `map(ResponseEntity::ok)` → `Optional<ResponseEntity<PatientResponse>>` with status 200.
5. If empty: `orElse(notFound())` → 404 with no body.

**`Optional` chain:** same pattern as before — only added mapper in the middle.

---

#### POST `/api/patients` — create

```java
@PostMapping
public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientCreateRequest request) {
    var created = patientService.create(PatientMapper.toEntity(request));
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(PatientMapper.toResponse(created));
}
```

**Step by step:**

1. `@RequestBody PatientCreateRequest request` — JSON → Java DTO (not `Patient` anymore).
2. `@Valid` — run `@NotBlank` on the DTO; fail → 400 via `GlobalExceptionHandler`.
3. `PatientMapper.toEntity(request)` — DTO → `Patient` entity.
4. `patientService.create(...)` — business rules (duplicate id, etc.) → saves to MySQL → returns `Patient`.
5. `PatientMapper.toResponse(created)` — entity → DTO for response.
6. `status(CREATED)` — HTTP **201** (not 200).

**Flow diagram:**

```
JSON body
  → PatientCreateRequest  (@Valid)
  → Patient               (mapper)
  → Patient               (service.save)
  → PatientResponse       (mapper)
  → JSON response
```

---

#### PUT `/api/patients/{id}` — update

```java
@PutMapping("/{id}")
public ResponseEntity<PatientResponse> update(
        @PathVariable String id,
        @Valid @RequestBody PatientUpdateRequest request) {
    return patientService.update(id, PatientMapper.toEntity(id, request))
            .map(PatientMapper::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
```

**Step by step:**

1. `id` from URL path.
2. `request` from body (names only).
3. `PatientMapper.toEntity(id, request)` — **merges** path id + body names → one `Patient`.
4. `service.update(id, patient)` — if id not in DB → `Optional.empty()` → 404.
5. If ok → map to `PatientResponse` → 200.

**Key idea:** Two sources of data combined in the mapper:

- **Path:** `id`
- **Body:** `givenName`, `familyName`

---

#### DELETE `/api/patients/{id}` — unchanged

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable String id) {
    if (patientService.delete(id)) {
        return ResponseEntity.noContent().build();  // 204
    }
    return ResponseEntity.notFound().build();       // 404
}
```

No DTO — no request body, no response body. Same as before.

---

### 6. `Patient.java` (entity) — what changed

You correctly **removed** `@NotBlank` from the entity.

```java
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    private String id;
    private String givenName;
    private String familyName;
    // ...
}
```

**Validation moved** from entity → DTO (`PatientCreateRequest`, `PatientUpdateRequest`).

**Service** (`PatientServiceImpl`) still uses `Patient` — no changes needed there.

---

## Part D — How JSON, Jackson, and `@Valid` work together

When Postman sends:

```http
POST /api/patients
Content-Type: application/json

{"id":"99","givenName":"A","familyName":"B"}
```

Spring Boot does this internally:

```
1. Read raw JSON string from HTTP body
2. Jackson: JSON → PatientCreateRequest (uses empty ctor + setters)
3. @Valid: check @NotBlank on each field
4. If invalid → MethodArgumentNotValidException → GlobalExceptionHandler → 400 ApiError
5. If valid → your create() method runs
6. Return PatientResponse → Jackson → JSON string in HTTP response
```

**GlobalExceptionHandler** (unchanged) still catches validation errors — but now the errors refer to DTO field names (`givenName`, not entity fields).

---

## Part E — Old vs new (side by side)

### POST create

**Old:**

```java
public ResponseEntity<Patient> create(@Valid @RequestBody Patient patient) {
    Patient created = patientService.create(patient);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}
```

**New:**

```java
public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientCreateRequest request) {
    var created = patientService.create(PatientMapper.toEntity(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(PatientMapper.toResponse(created));
}
```

**What changed:** Type of `@RequestBody`, type of response, two mapper calls in the middle.

### PUT update

**Old:** body was full `Patient` (client could send wrong `id` in body).  
**New:** body is `PatientUpdateRequest` (no id); path id is authoritative.

---

## Part F — Small fixes before testing tomorrow

| # | File | Issue | Fix |
|---|------|-------|-----|
| 1 | `PatientResponse.java` | Missing `getId()` / `setId()` | Add both (see Part C.3) |
| 2 | `PatientCreateRequest.java` | `getFamilyName()` not public | Add `public` |
| 3 | `PatientController.java` | `@RequestMapping("api/patients")` | Prefer `"/api/patients"` |
| 4 | `PatientController.java` | Old code commented at top (lines 1–63) | Optional: delete comment block once you understand |

Then:

```bash
./mvnw compile
./mvnw spring-boot:run
```

---

## Part G — Test plan for tomorrow

### 1. GET list (should include `id` on each patient)

```bash
curl http://localhost:8080/api/patients
```

### 2. POST create

```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{"id":"99","givenName":"Test","familyName":"User"}'
```

Expect: **201** and JSON with `id`, `givenName`, `familyName`.

### 3. PUT update (no id in body)

```bash
curl -X PUT http://localhost:8080/api/patients/99 \
  -H "Content-Type: application/json" \
  -d '{"givenName":"Updated","familyName":"Name"}'
```

### 4. Validation 400

```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{"id":"100","familyName":"Only"}'
```

Expect: `"Validation failed"` and `fieldErrors.givenName`.

### 5. DELETE

```bash
curl -X DELETE http://localhost:8080/api/patients/99
```

Expect: **204** No Content.

---

## Part H — Questions to answer tomorrow (check understanding)

Before moving to Visit DTOs, you should be able to explain:

1. Why do we have **three** DTO classes instead of one?
2. Why does `PatientUpdateRequest` have **no** `id` field?
3. What does `PatientMapper.toEntity(id, request)` do on PUT?
4. Where does `@Valid` run — on `Patient` or on `PatientCreateRequest`?
5. Why does `PatientCreateRequest` need an **empty constructor**?
6. What class does `PatientService.create()` accept — DTO or entity?
7. What happens if you remove `PatientMapper` and return `Patient` from the controller again?

---

## Part I — After DTOs sink in (next topics)

1. **Fix + test Patient DTOs** (tomorrow)
2. **Same pattern for Visit** — `VisitCreateRequest`, `VisitUpdateRequest`, `VisitResponse`, `VisitMapper`
3. **`@ManyToOne`** — Visit → Patient JPA relationship
4. **OpenMRS compare** — `AppointmentRequest` vs your DTOs
5. Optional: OpenMRS module first endpoint

---

## Quick reference — file locations

```
web/dto/
  ApiError.java              ← error JSON (already existed)
  PatientCreateRequest.java  ← POST in
  PatientUpdateRequest.java  ← PUT in
  PatientResponse.java       ← all responses out
  PatientMapper.java         ← conversions

web/PatientController.java   ← uses DTOs only (not Patient in signatures)

model/Patient.java           ← entity, JPA only, no @NotBlank

service/PatientServiceImpl.java  ← unchanged, still uses Patient
```

---

## Study files on disk

| File | Purpose |
|------|---------|
| `docs/SESSION-REVISION-2026-05-31.pdf` | Earlier sessions revision |
| `postman/clinic-practice.postman_collection.json` | API tests |
| `postman/POSTMAN-TEST-DATA.md` | Sample JSON bodies |

---

*End of where-we-left-off — resume with DTO explanation + testing.*
