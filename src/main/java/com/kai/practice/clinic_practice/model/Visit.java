package com.kai.practice.clinic_practice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    private String id;
    
    //private String patientId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

     //private String providerId;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private LocalDate visitDate;
    private String reason;

    public Visit() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getPatientId() {
         return patient != null ? patient.getId() : null;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }



    public Provider getProvider() {
        return provider;
    }

    public String getProviderId() {
        return provider != null ? provider.getId() : null;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



}
