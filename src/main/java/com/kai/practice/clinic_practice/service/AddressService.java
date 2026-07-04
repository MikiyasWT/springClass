package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Address;
import com.kai.practice.clinic_practice.web.dto.address.*;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<Address> getByPatientId(String patientId);

    Address create(String patientId, AddressCreateRequest address);

    Optional<Address> update(String patientId, AddressUpdateRequest address);

    boolean delete(String id);
}