package com.kai.practice.clinic_practice.web;

import com.kai.practice.clinic_practice.model.Visit;
import com.kai.practice.clinic_practice.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

     private final VisitService visitService;

     public VisitController(VisitService visitService) {
         this.visitService = visitService;
     }

    @GetMapping
    public List<Visit> list() {
         return visitService.getAll();
     }

     @GetMapping("/patient/{patientId}")
     public List<Visit> byPatient(@PathVariable String patientId) {
         return visitService.getByPatientId(patientId);
     }

     @GetMapping("/{id}")
     public ResponseEntity<Visit> one(@PathVariable String id) {
          return visitService.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
     }

     @PostMapping
     public ResponseEntity<Visit> create(@Valid @RequestBody Visit visit) {
        Visit created =visitService.create(visit);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
     }

     @PutMapping("/{id}")
     public ResponseEntity<Visit> update(@PathVariable String id, @RequestBody Visit visit) {
              return visitService.update(id, visit)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> delete (@PathVariable String id) {
         if(visitService.delete(id)) {
            return ResponseEntity.noContent().build();
         }
         return ResponseEntity.notFound().build();
     }
}