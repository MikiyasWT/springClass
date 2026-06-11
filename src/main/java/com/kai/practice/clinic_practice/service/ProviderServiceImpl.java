package com.kai.practice.clinic_practice.service;


import com.kai.practice.clinic_practice.model.Provider;
import com.kai.practice.clinic_practice.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
public class ProviderServiceImpl implements ProviderService {
    
     public static final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);
     private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
      this.providerRepository = providerRepository;
    }


     @Override
     public List<Provider> getAll() {
       return providerRepository.findAll();
     }

     @Override
     public Optional<Provider> getById(String id) {
       return providerRepository.findById(id);
     }


     @Override
     public Provider create(Provider provider) {
        if(provider.getId() == null || provider.getId().isBlank()) {
            throw new IllegalArgumentException("Provider id is required");
        }
        if(providerRepository.existsById(provider.getId())) {
            throw new IllegalArgumentException("Provider id already exists: " + provider.getId());
        }

        log.warn("Created Provider id={}", provider.getId());
        return providerRepository.save(provider);
     }


     @Override
     public Optional<Provider> update(String id, Provider provider) {
        if(!providerRepository.existsById(id)) {
             return Optional.empty();
        }
        if(provider.getGivenName() == null || provider.getGivenName().isBlank()) {
             throw new IllegalArgumentException("Provider given name is required");
        }
        if(provider.getFamilyName() == null || provider.getFamilyName().isBlank()) {
             throw new IllegalArgumentException("Provider family name is required");
        }
        if(provider.getSpecialty() == null || provider.getSpecialty().isBlank()) {
             throw new IllegalArgumentException("Provider Specialty is required");
        }

        Provider toBeUpdated = new Provider(
            id,
            provider.getGivenName(),
            provider.getFamilyName(),
            provider.getSpecialty()
        );

        return Optional.of(providerRepository.save(toBeUpdated));
     }

     @Override
     public boolean delete(String id) {
          if(!providerRepository.existsById(id)){
               return false;  
          }

          providerRepository.deleteById(id);
          return true;
     }
   
}