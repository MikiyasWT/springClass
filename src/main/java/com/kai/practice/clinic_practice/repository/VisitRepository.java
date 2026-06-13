package com.kai.practice.clinic_practice.repository;

import com.kai.practice.clinic_practice.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, String> {

    List<Visit> findByPatientId(String patientId);

    List<Visit> findByProviderId(String providerId);
}
