package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Visit;
import com.kai.practice.clinic_practice.service.VisitService;
import com.kai.practice.clinic_practice.repository.PatientRepository;
import com.kai.practice.clinic_practice.repository.VisitRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class VisitServiceImpl implements VisitService {

    private static final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;
    private final PatientRepository  patientRepository; 
    

    public VisitServiceImpl(VisitRepository visitRepository, PatientRepository  patientRepository) {
        this.visitRepository =  visitRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Visit> getAll() {
       return visitRepository.findAll();
    }

    @Override
    public Optional<Visit> getById(String id) {
          return visitRepository.findById(id);
    }

    @Override
    public List<Visit> getByPatientId(String patientId) {
       return visitRepository.findByPatientId(patientId);
    }

    @Override
    public Visit create(Visit visit) {
     if(visit.getId() == null || visit.getId().isBlank()) {
           throw new IllegalArgumentException("Visit id is required");
     } 

     if(!patientRepository.existsById(visit.getPatientId())) {
         throw new IllegalArgumentException("Patient not found: "+ visit.getPatientId());
     }
     if(visitRepository.existsById(visit.getId())) {
        throw new IllegalArgumentException("Visit id already exisits: "+ visit.getId());
     }

     Visit saved = visitRepository.save(visit);
     log.warn("Created visit");
     return saved;
    }


    @Override
    public Optional<Visit> update(String id, Visit visit) {
        if(!visitRepository.existsById(id)) {
            return Optional.empty();
        }
        if (!patientRepository.existsById(visit.getPatientId())) {
           throw new IllegalArgumentException("Patient not found");
        }

        Visit updated = new Visit(id, visit.getPatientId(), visit.getVisitDate(), visit.getReason());
        visitRepository.save(updated);
        log.warn("Updated visit id ={}", id);
        return Optional.of(updated);
    }

    @Override
    public boolean delete(String id) {
        if(!visitRepository.existsById(id)){
            return false;
        }
         visitRepository.deleteById(id);
         log.warn("Deleted visit id={}", id);
         return true;
    }
    
}

