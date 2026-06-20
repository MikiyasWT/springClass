package com.kai.practice.clinic_practice.repository;

import com.kai.practice.clinic_practice.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, String> {

    List<Visit> findByPatient_Id(String patientId);

    List<Visit> findByProvider_Id(String providerId);
}
