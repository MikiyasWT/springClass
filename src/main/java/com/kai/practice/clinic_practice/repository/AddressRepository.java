package com.kai.practice.clinic_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kai.practice.clinic_practice.model.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {

   Optional<Address> findByPatient_Id(String patientId);
}