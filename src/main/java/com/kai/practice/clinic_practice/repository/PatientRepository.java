package com.kai.practice.clinic_practice.repository;

import com.kai.practice.clinic_practice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {

}