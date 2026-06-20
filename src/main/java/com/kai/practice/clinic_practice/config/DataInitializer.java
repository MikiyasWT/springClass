package com.kai.practice.clinic_practice.config;

import com.kai.practice.clinic_practice.model.Patient;
import com.kai.practice.clinic_practice.model.Visit;
import com.kai.practice.clinic_practice.model.Provider;
import com.kai.practice.clinic_practice.repository.PatientRepository;
import com.kai.practice.clinic_practice.repository.VisitRepository;
import com.kai.practice.clinic_practice.repository.ProviderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;
    private final ProviderRepository providerRepository;

    public DataInitializer(PatientRepository patientRepository, VisitRepository visitRepository, ProviderRepository providerRepository) {
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public void run(String... args) {
        if(patientRepository.count() == 0) {
              patientRepository.save(new Patient("1", "First", "Patient"));
              patientRepository.save(new Patient("2", "Second", "Patient"));
        }

        if(providerRepository.count() == 0) {
            providerRepository.save(new Provider("1", "Proivder one givenName", "Provider one familyName", "MCH"));
             providerRepository.save(new Provider("2", "provider two givenName", "provider two familyName", "Dentistry"));
        }

        if(visitRepository.count() == 0) {
            //   visitRepository.save(new Visit("v1", "1", "1", LocalDate.of(2026, 5, 1), "Initial consultation"));
            //   visitRepository.save(new Visit("v2", "1", "1", LocalDate.of(2026, 5, 20), "Follow-up"));
            Patient patient1 = patientRepository.findById("1").orElseThrow();
            Provider provider1 = providerRepository.findById("1").orElseThrow();

            Visit v1 = new Visit();
            v1.setId("v1");
            v1.setPatient(patient1);
            v1.setProvider(provider1);
            v1.setVisitDate(LocalDate.of(2026, 5, 1));
            v1.setReason("Inital Consultation");
            visitRepository.save(v1);

            Visit v2 = new Visit();
            v2.setId("v2");
            v2.setPatient(patient1);
            v2.setProvider(provider1);
            v2.setVisitDate(LocalDate.of(2026, 5, 20));
            v2.setReason("Follow Up");
            visitRepository.save(v2);
        }

    }
}