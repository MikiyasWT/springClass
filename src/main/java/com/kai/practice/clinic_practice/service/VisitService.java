package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Visit;
import java.util.List;
import java.util.Optional;

public interface VisitService {

    List<Visit> getAll();

    Optional<Visit> getById(String id);

    List<Visit> getByProviderId(String providerId);

    List<Visit> getByPatientId(String patientId);

    Visit create(Visit visit);

    Optional<Visit> update( String id, Visit visit);

    boolean delete(String id);
}