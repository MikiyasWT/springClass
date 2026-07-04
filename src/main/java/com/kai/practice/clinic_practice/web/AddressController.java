package com.kai.practice.clinic_practice.web;

import com.kai.practice.clinic_practice.service.AddressService;
import com.kai.practice.clinic_practice.web.dto.address.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/patients/{patientId}/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
           this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<AddressResponse> get(@PathVariable String patientId){
          return addressService.getByPatientId(patientId)
          .map(AddressMapper::toResponse)
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AddressResponse> create(@PathVariable String patientId, @Valid @RequestBody AddressCreateRequest request) {
        var created = addressService.create(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(AddressMapper.toResponse(created));
    }

    @PutMapping
    public ResponseEntity<AddressResponse> update(@PathVariable String patientId, @Valid @RequestBody AddressUpdateRequest request) {
        return addressService.update(patientId, request)
               .map(AddressMapper::toResponse)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping
    public ResponseEntity<Void> delete (@PathVariable String patientId) {
        if(addressService.delete(patientId)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}