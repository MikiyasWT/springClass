package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Patient;
import com.kai.practice.clinic_practice.model.Provider;
import com.kai.practice.clinic_practice.model.Visit;
import com.kai.practice.clinic_practice.repository.PatientRepository;
import com.kai.practice.clinic_practice.repository.ProviderRepository;
import com.kai.practice.clinic_practice.repository.VisitRepository;
import com.kai.practice.clinic_practice.web.dto.visit.VisitCreateRequest;
import com.kai.practice.clinic_practice.web.dto.visit.VisitMapper;
import com.kai.practice.clinic_practice.web.dto.visit.VisitUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    private static final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final ProviderRepository providerRepository;

    public VisitServiceImpl(VisitRepository visitRepository,
                            PatientRepository patientRepository,
                            ProviderRepository providerRepository) {
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
        this.providerRepository = providerRepository;
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
        return visitRepository.findByPatient_Id(patientId);
    }

    @Override
    public List<Visit> getByProviderId(String providerId) {
        return visitRepository.findByProvider_Id(providerId);
    }

    @Override
    public Visit create(VisitCreateRequest request) {
        if (request.getId() == null || request.getId().isBlank()) {
            throw new IllegalArgumentException("Visit id is required");
        }
        if (visitRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("Visit id already exists: " + request.getId());
        }

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Patient not found: " + request.getPatientId()));

        Provider provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No such provider: " + request.getProviderId()));

        Visit visit = VisitMapper.toEntity(request);
        visit.setPatient(patient);
        visit.setProvider(provider);

        log.warn("Created visit id={}", visit.getId());
        return visitRepository.save(visit);
    }

    @Override
    public Optional<Visit> update(String id, VisitUpdateRequest request) {
        if (!visitRepository.existsById(id)) {
            return Optional.empty();
        }

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Provider provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No such provider: " + request.getProviderId()));

        Visit visit = VisitMapper.toEntity(id, request);
        visit.setPatient(patient);
        visit.setProvider(provider);

        Visit saved = visitRepository.save(visit);
        log.warn("Updated visit id={}", id);
        return Optional.of(saved);
    }

    @Override
    public boolean delete(String id) {
        if (!visitRepository.existsById(id)) {
            return false;
        }
        visitRepository.deleteById(id);
        log.warn("Deleted visit id={}", id);
        return true;
    }
}
