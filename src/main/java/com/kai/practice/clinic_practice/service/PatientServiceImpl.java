package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Patient;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PatientServiceImpl implements PatientService {
  private final Map<String, Patient> store = new LinkedHashMap<>();
  private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

  public PatientServiceImpl() {
    store.put("1", new Patient("1", "Andient", "Tesfa"));
    store.put("2", new Patient("2","jonas", "Tesfaye"));
  }

  @Override
  public List<Patient> getAll() {
    return new ArrayList<>(store.values());
  }
 
  @Override
  public Optional<Patient> getById(String id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Patient create(Patient patient) {
        if (patient.getId() == null || patient.getId().isBlank()) {
            log.warn("Patient id is required");
            throw new IllegalArgumentException("Patient id is required");
        }
        if (store.containsKey(patient.getId())) {
            log.warn("Patient id already exists: " + patient.getId());
            throw new IllegalArgumentException("Patient id already exists: " + patient.getId());
        }
        store.put(patient.getId(), patient);
        log.warn("Created patient id={} name={} {}",
                patient.getId(), patient.getGivenName(), patient.getFamilyName());
        return patient;
    }
    
   @Override
   public Optional<Patient> update(String id, Patient patient) {
    if(!store.containsKey(id)) {
        return Optional.empty();
    }
    if(patient.getGivenName() == null || patient.getGivenName().isBlank()) {
        log.warn("given name is blank");
        throw new IllegalArgumentException("given name is required");
    }

    if(patient.getFamilyName() == null || patient.getFamilyName().isBlank()) {
        log.warn("family name cannot be blank");
        throw new IllegalArgumentException("family name can't be blank");
    }
    Patient updated = new Patient(id, patient.getGivenName(), patient.getFamilyName());

    store.put(id, updated);
    log.warn("updated patient id={}", id);
    return Optional.of(updated);
    
    }


    public boolean delete(String id) {
        if(!store.containsKey(id)) {
            log.warn("no such patient");

        } 

        store.remove(id);
        log.warn("Deleted patient id={}", id);
        return true;

    }

}