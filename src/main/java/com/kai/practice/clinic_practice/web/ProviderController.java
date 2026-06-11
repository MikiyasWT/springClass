package com.kai.practice.clinic_practice.web;

import com.kai.practice.clinic_practice.service.ProviderService;
import org.springframework.web.bind.annotation.*;
import com.kai.practice.clinic_practice.web.dto.provider.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("api/providers")
public class ProviderController {

     private final ProviderService providerService;
     
     public ProviderController(ProviderService providerService) {
       this.providerService = providerService;
     }

     @GetMapping
     public List<ProviderResponse> list() {
       return providerService.getAll()
                 .stream()
                 .map(ProviderMapper::toResponse)
                 .toList();
     }


     @GetMapping("/{id}")
     public ResponseEntity<ProviderResponse> one(@PathVariable String id) {
      return providerService.getById(id)
                .map(ProviderMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
     }

     @PostMapping
     public ResponseEntity<ProviderResponse> create(@Valid @RequestBody ProviderCreateRequest request){
          var created = providerService.create(ProviderMapper.toEntity(request));
          return ResponseEntity.status(HttpStatus.CREATED).body(ProviderMapper.toResponse(created));
     }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponse> update(@PathVariable String id, @Valid @RequestBody ProviderUpdateRequest request) {
        return providerService.update(id, ProviderMapper.toEntity(id,request))
                .map(ProviderMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if(providerService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}