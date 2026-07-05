package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Patient;
import com.kai.practice.clinic_practice.model.Visit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kai.practice.clinic_practice.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {
  private final PatientRepository patientRepository;  
  //private final Map<String, Patient> store = new LinkedHashMap<>();
  private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

  public PatientServiceImpl(PatientRepository patientRepository) {
     this.patientRepository = patientRepository;
  }

  @Override
  public List<Patient> getAll() {
    return patientRepository.findAll();
  }
 
  @Override
  public Optional<Patient> getById(String id) {
    return patientRepository.findById(id);
  }

  @Override
  public Patient create(Patient patient) {
        
        if (patient.getId() == null || patient.getId().isBlank()) {
            log.warn("Patient id is required");
            throw new IllegalArgumentException("Patient id is required");
        }
        if (patientRepository.existsById(patient.getId())) {
            log.warn("Patient id already exists: " + patient.getId());
            throw new IllegalArgumentException("Patient id already exists: " + patient.getId());
        }
        log.warn("Created patient id={} name={} {}",
                patient.getId(), patient.getGivenName(), patient.getFamilyName());
        return patientRepository.save(patient);
    }
    
   @Override
   public Optional<Patient> update(String id, Patient patient) {
    
    if(!patientRepository.existsById(id)) {
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

    return Optional.of(patientRepository.save(updated));
    
    }


    @Override
    public boolean delete(String id) {
        if (!patientRepository.existsById(id)) {
            return false;
        }
        patientRepository.deleteById(id);
        return true;

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Visit>> getVisitsByPatientId(String patientId) {
          return patientRepository.findById(patientId)
            .map(patient -> {
                List<Visit> visits = patient.getVisits();
                return visits != null? visits : List.of();
            });
    }
}
