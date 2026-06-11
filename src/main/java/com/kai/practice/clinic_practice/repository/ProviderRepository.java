package com.kai.practice.clinic_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kai.practice.clinic_practice.model.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {

}
