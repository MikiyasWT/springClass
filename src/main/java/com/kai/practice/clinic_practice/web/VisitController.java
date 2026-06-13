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
import com.kai.practice.clinic_practice.web.dto.visit.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

     private final VisitService visitService;

     public VisitController(VisitService visitService) {
         this.visitService = visitService;
     }

    @GetMapping
    public List<VisitResponse> list() {
         return visitService.getAll()
                            .stream()
                            .map(VisitMapper::toResponse)
                            .toList();
     }

     @GetMapping("/patient/{patientId}")
     public List<VisitResponse> byPatient(@PathVariable String patientId) {
         return visitService.getByPatientId(patientId)
                            .stream()
                            .map(VisitMapper::toResponse)
                            .toList();
     }

     @GetMapping("/{id}")
     public ResponseEntity<VisitResponse> one(@PathVariable String id) {
          return visitService.getById(id)
                             .map(VisitMapper::toResponse)
                             .map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
     }

     @PostMapping
     public ResponseEntity<VisitResponse> create(@Valid @RequestBody VisitCreateRequest request) {
        Visit created = visitService.create(VisitMapper.toEntity(request));                      
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(VisitMapper.toResponse(created));
     }

     @PutMapping("/{id}")
     public ResponseEntity<VisitResponse> update(@PathVariable String id, @Valid @RequestBody VisitUpdateRequest request) {
                    return visitService.update(id, VisitMapper.toEntity(id, request))
                           .map(VisitMapper::toResponse)
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