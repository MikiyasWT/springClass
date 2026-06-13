package com.kai.practice.clinic_practice.web.dto.visit;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


public class VisitUpdateRequest {


    @NotBlank(message = "patient id is required")
    private String patientId;

    @NotBlank(message = "provder id is required")
    private String providerId;

    private LocalDate visitDate;

    @NotBlank(message = "reason is required")
    private String reason;


    public VisitUpdateRequest() { }

    public VisitUpdateRequest (String patientId, String providerId, LocalDate visitDate, String reason) {
        this.patientId = patientId;
        this.providerId = providerId;
        this.visitDate = visitDate;
        this.reason = reason; 
    }

    public String getPatientId () {
        return patientId;
    }

    public void setPatientId (String patientId) {
          this.patientId = patientId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public LocalDate getVisitDate () {
        return visitDate;
    }

    public void setVisitDate (LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getReason  () {
        return reason;
    }

    public void setReason (String reason) {
        this.reason = reason;
    }
}