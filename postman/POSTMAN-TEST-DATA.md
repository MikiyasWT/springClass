# Postman test data — Clinic Practice API

**Base URL:** `http://localhost:8080`  
**Header for POST/PUT:** `Content-Type: application/json`

Import collection: **File → Import →** `postman/clinic-practice.postman_collection.json`

---

## Suggested run order

1. Start Docker: `docker compose up -d`
2. Start app: `./mvnw spring-boot:run`
3. Run requests top to bottom in each folder (Patients → Visits → **Providers**)

---

## Seed data (from `DataInitializer` on empty DB)

| Type | id | Details |
|------|-----|---------|
| Patient | `1` | First Patient |
| Patient | `2` | Second Patient |
| Visit | `v1` | patient `1`, 2026-05-01, Initial consultation |
| Visit | `v2` | patient `1`, 2026-05-20, Follow-up |
| Provider | `1` | MCH specialty (seed names updated by PUT test) |
| Provider | `2` | Dentistry |

---

## Providers — suggested run order

Run the **Providers** folder in Postman **top to bottom**. Each request includes automated status checks.

| # | Request | Expected |
|---|---------|----------|
| 1 | GET list | 200, JSON array |
| 2 | GET by id `1` | 200, specialty `MCH` (or `General Practice` after PUT) |
| 3 | GET by id `2` | 200, specialty `Dentistry` |
| 4 | GET missing id | 404 |
| 5 | POST create `dr3` | 201 |
| 6 | POST create `dr4` | 201 |
| 7 | PUT update id `1` | 200 |
| 8 | PUT missing id | 404 |
| 9 | POST empty givenName | 400 + fieldErrors |
| 10 | POST missing specialty | 400 + fieldErrors |
| 11 | PUT empty specialty | 400 + fieldErrors |
| 12 | POST duplicate id `1` | 400 + already exists |
| 13 | DELETE `dr3` | 204 |
| 14 | DELETE `dr4` | 204 |
| 15 | DELETE missing id | 404 |
| 16 | GET `dr3` after delete | 404 |

---

## Patients

### GET list
```
GET http://localhost:8080/api/patients
```

### GET one
```
GET http://localhost:8080/api/patients/1
```

### POST create — Helen
```json
{
  "id": "p10",
  "givenName": "Helen",
  "familyName": "Bekele"
}
```

### POST create — Daniel
```json
{
  "id": "p11",
  "givenName": "Daniel",
  "familyName": "Alemu"
}
```

### PUT update patient 1 (no id in body)
```
PUT http://localhost:8080/api/patients/1
```
```json
{
  "givenName": "Meron",
  "familyName": "Tadesse"
}
```

### DELETE
```
DELETE http://localhost:8080/api/patients/p11
```

### POST validation error (400)
```json
{
  "id": "p99",
  "givenName": "",
  "familyName": "Test"
}
```

### POST duplicate id (400)
```json
{
  "id": "1",
  "givenName": "Duplicate",
  "familyName": "Patient"
}
```

---

## Visits

### GET list
```
GET http://localhost:8080/api/visits
```

### GET by patient
```
GET http://localhost:8080/api/visits/patient/1
```

### GET one
```
GET http://localhost:8080/api/visits/v1
```

### POST create (patient must exist — run patient POST first for p10)
```json
{
  "id": "v10",
  "patientId": "1",
  "visitDate": "2026-05-31",
  "reason": "Blood pressure check"
}
```

### POST for patient p10
```json
{
  "id": "v11",
  "patientId": "p10",
  "visitDate": "2026-06-01",
  "reason": "First visit after registration"
}
```

### PUT update v10
```
PUT http://localhost:8080/api/visits/v10
```
```json
{
  "patientId": "1",
  "visitDate": "2026-05-31",
  "reason": "Blood pressure check — follow-up notes added"
}
```

### DELETE
```
DELETE http://localhost:8080/api/visits/v11
```

### POST patient not found (400)
```json
{
  "id": "v99",
  "patientId": "no-such-patient",
  "visitDate": "2026-05-31",
  "reason": "Should fail"
}
```

---

## Providers

**Base path:** `GET/POST /api/providers` · `GET/PUT/DELETE /api/providers/{id}`

### GET list
```
GET http://localhost:8080/api/providers
```

### GET one (seed)
```
GET http://localhost:8080/api/providers/1
GET http://localhost:8080/api/providers/2
```

### POST create — Amina (dr3)
```json
{
  "id": "dr3",
  "givenName": "Amina",
  "familyName": "Hassan",
  "specialty": "Dermatology"
}
```

### POST create — Yonas (dr4)
```json
{
  "id": "dr4",
  "givenName": "Yonas",
  "familyName": "Kebede",
  "specialty": "Cardiology"
}
```

### PUT update provider 1 (no id in body)
```
PUT http://localhost:8080/api/providers/1
```
```json
{
  "givenName": "Sara",
  "familyName": "Bekele",
  "specialty": "General Practice"
}
```

### DELETE
```
DELETE http://localhost:8080/api/providers/dr3
DELETE http://localhost:8080/api/providers/dr4
```

### POST validation error — empty givenName (400)
```json
{
  "id": "dr99",
  "givenName": "",
  "familyName": "Test",
  "specialty": "Surgery"
}
```

### POST validation error — missing specialty (400)
```json
{
  "id": "dr98",
  "givenName": "Test",
  "familyName": "Provider"
}
```

### PUT validation error — empty specialty (400)
```
PUT http://localhost:8080/api/providers/2
```
```json
{
  "givenName": "Valid",
  "familyName": "Name",
  "specialty": ""
}
```

### POST duplicate id (400)
```json
{
  "id": "1",
  "givenName": "Duplicate",
  "familyName": "Provider",
  "specialty": "MCH"
}
```

---

## Expected status codes

| Request | Success | Error |
|---------|---------|-------|
| GET list / one | 200 | 404 |
| POST create | 201 | 400 |
| PUT update | 200 | 404 / 400 |
| DELETE | 204 | 404 |

Providers use the same status codes. Provider POST/PUT bodies require `givenName`, `familyName`, and `specialty` (create also requires `id`).
