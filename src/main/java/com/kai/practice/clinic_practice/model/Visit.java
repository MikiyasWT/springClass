package com.kai.practice.clinic_practice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    private String id;
    private String patientId;

    private String providerId;

    private LocalDate visitDate;
    private String reason;

    public Visit() {
    }

    public Visit(String id, String patientId, String providerId, LocalDate visitDate, String reason) {
        this.id = id;
        this.patientId = patientId;
        this.providerId = providerId;
        this.visitDate = visitDate;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
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
