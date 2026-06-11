package com.kai.practice.clinic_practice.service;

import com.kai.practice.clinic_practice.model.Provider;
import java.util.List;
import java.util.Optional;

public interface ProviderService {

     List<Provider> getAll();

     Optional<Provider> getById(String id);

     Provider create(Provider provider);

     Optional<Provider> update(String id, Provider provider);

     boolean delete(String id);

}