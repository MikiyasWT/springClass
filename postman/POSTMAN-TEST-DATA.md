# Postman test data — Clinic Practice API

**Base URL:** `http://localhost:8080`  
**Header for POST/PUT:** `Content-Type: application/json`

Import collection: **File → Import →** `postman/clinic-practice.postman_collection.json`

---

## Suggested run order

1. Start Docker: `docker compose up -d`
2. Start app: `./mvnw spring-boot:run`
3. Run requests top to bottom in each folder (Patients, then Visits)

---

## Seed data (from `DataInitializer` on empty DB)

| Type | id | Details |
|------|-----|---------|
| Patient | `1` | First Patient |
| Patient | `2` | Second Patient |
| Visit | `v1` | patient `1`, 2026-05-01, Initial consultation |
| Visit | `v2` | patient `1`, 2026-05-20, Follow-up |

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

## Expected status codes

| Request | Success | Error |
|---------|---------|-------|
| GET list / one | 200 | 404 |
| POST create | 201 | 400 |
| PUT update | 200 | 404 / 400 |
| DELETE | 204 | 404 |
