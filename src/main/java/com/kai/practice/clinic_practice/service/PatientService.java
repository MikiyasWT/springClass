package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getAll();

    Optional<Patient> getById(String id);

    Patient create(Patient patient);

    Optional<Patient> update(String id, Patient patient);

    boolean delete(String id);
}