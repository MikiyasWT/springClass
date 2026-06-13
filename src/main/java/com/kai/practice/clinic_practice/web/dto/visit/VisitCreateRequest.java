package com.kai.practice.clinic_practice.web.dto.visit;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;


public class VisitCreateRequest {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "patient id is required")
    private String patientId;

    @NotBlank(message = "provider id is required")
    private String providerId;

    private LocalDate visitDate;

    @NotBlank(message = "reason can't be blank")
    private String reason;



    public VisitCreateRequest () { }
    
    public VisitCreateRequest (String id, String patientId, String providerId, LocalDate visitDate, String reason) { 
        this.id = id;
        this.patientId = patientId;
        this.providerId = providerId;
        this.visitDate = visitDate;
        this.reason = reason;
    }

    public String getId () {
        return id;
    }


    public void setId (String id) {
         this.id = id;
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
             