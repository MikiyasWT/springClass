package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Visit;
import com.kai.practice.clinic_practice.web.dto.visit.VisitCreateRequest;
import com.kai.practice.clinic_practice.web.dto.visit.VisitUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface VisitService {

    List<Visit> getAll();

    Optional<Visit> getById(String id);

    List<Visit> getByProviderId(String providerId);

    List<Visit> getByPatientId(String patientId);

    // Visit create(Visit visit);

    // Optional<Visit> update( String id, Visit visit);

    Visit create(VisitCreateRequest request);

    Optional<Visit> update( String id, VisitUpdateRequest visit);

    boolean delete(String id);
}