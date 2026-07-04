package com.kai.practice.clinic_practice.service;


import com.kai.practice.clinic_practice.service.AddressService;


import com.kai.practice.clinic_practice.model.Address;
import com.kai.practice.clinic_practice.model.Patient;
import com.kai.practice.clinic_practice.repository.AddressRepository;
import com.kai.practice.clinic_practice.repository.PatientRepository;

import com.kai.practice.clinic_practice.web.dto.address.*;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AddressServiceImpl implements AddressService {

   private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

   private final AddressRepository addressRepository;
   private final PatientRepository patientRepository;

   public AddressServiceImpl(AddressRepository addressRepository, PatientRepository patientRepository) {
         this.addressRepository = addressRepository;
         this.patientRepository = patientRepository;
   }


   @Override
   public Optional<Address> getByPatientId(String patientId) {
          return addressRepository.findByPatient_Id(patientId);
   }

   @Override
   public Address create(String patientId, AddressCreateRequest request){
       if(!patientRepository.existsById(patientId)) {
          throw new IllegalArgumentException("Patient not found: " + patientId);
       }
       if(addressRepository.findByPatient_Id(patientId).isPresent()){
          throw new IllegalArgumentException("patient already has an address");
       }
       if(addressRepository.existsById(request.getId())){
          throw new IllegalArgumentException("Address id already exists: " + request.getId());
       }

       Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
       Address address = AddressMapper.toEntity(request);
       address.setPatient(patient);

       log.warn("Created address id={} for patient id={}", address.getId(), patientId);
       return addressRepository.save(address);
   }

   @Override
   public Optional<Address> update(String patientId, AddressUpdateRequest request){

         //first make sure address with that patient Id exisits
         Optional<Address> existing =  addressRepository.findByPatient_Id(patientId);
         if(existing.isEmpty()){
             return Optional.empty();
         }
         
         // we make sure a patient with that patient Id exists
         Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));

         Address address = AddressMapper.toEntity(existing.get().getId(), request);
         address.setPatient(patient);

         Address saved = addressRepository.save(address);
         log.warn("Updated address id={} for patient id={}", saved.getId(), patientId);
         return Optional.of(saved);
   }

   @Override
   public boolean delete(String patientId){
       Optional<Address> existing =  addressRepository.findByPatient_Id(patientId);
       if(existing.isEmpty()){
           return false;
       }
       addressRepository.deleteById(existing.get().getId());
       log.warn("Deleted address id={} for patient id={}", existing.get().getId(), patientId);
       return true;
   }
    
}