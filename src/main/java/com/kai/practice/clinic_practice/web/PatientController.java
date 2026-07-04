//package com.kai.practice.clinic_practice.web;

// import com.kai.practice.clinic_practice.service.PatientService;
// import com.kai.practice.clinic_practice.model.Patient;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import jakarta.validation.Valid;

// import org.springframework.web.bind.annotation.RestController;
// import java.util.List;

// @RestController
// @RequestMapping("/api/patients")
// public class PatientController {

//    private final PatientService patientService;

//    public PatientController(PatientService patientService) {
//         this.patientService = patientService;
//    }

//    @GetMapping
//    public List<Patient> list() {
//        return patientService.getAll();
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Patient> one(@PathVariable String id) {
//       return patientService.getById(id)
//                  .map(ResponseEntity::ok)
//                  .orElse(ResponseEntity.notFound().build());
//    }

//    @PostMapping
//    public ResponseEntity<Patient> create(@Valid @RequestBody Patient patient) {
//       Patient created = patientService.create(patient);
//       return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Patient> update(@PathVariable String id, @Valid @RequestBody Patient patient) {
//       return patientService.update(id, patient)
//                  .map(ResponseEntity::ok)
//                  .orElse(ResponseEntity.notFound().build());
//    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id) {
//       if(patientService.delete(id)) {
//         return ResponseEntity.noContent().build();
//       }

//       return ResponseEntity.notFound().build();
//    }
// }







package com.kai.practice.clinic_practice.web;

import com.kai.practice.clinic_practice.service.PatientService;
import com.kai.practice.clinic_practice.web.dto.patient.PatientCreateRequest;
import com.kai.practice.clinic_practice.web.dto.patient.PatientMapper;
import com.kai.practice.clinic_practice.web.dto.patient.PatientResponse;
import com.kai.practice.clinic_practice.web.dto.patient.PatientUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController (PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientResponse> list() {
      return patientService.getAll()
                 .stream()
                 .map(PatientMapper::toResponse)
                 .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> one(@PathVariable String id) {
       return patientService.getById(id)
                .map(PatientMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientCreateRequest request) {
      var created = patientService.create(PatientMapper.toEntity(request));
      return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(PatientMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable String id,  @Valid @RequestBody PatientUpdateRequest request) {
      return patientService.update(id, PatientMapper.toEntity(id, request))
                 .map(PatientMapper::toResponse)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }
   

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable String id) {
       if (patientService.delete(id)) {
            return ResponseEntity.noContent().build();
       }
       return ResponseEntity.notFound().build();
    }

}

