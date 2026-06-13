package com.kai.practice.clinic_practice.web.dto.visit;
import java.time.LocalDate;


public class VisitResponse {
    
    private String id;
    private String patientId;
    private LocalDate visitDate;
    private String reason;

    public VisitResponse() { }

    public VisitResponse(String id, String patientId, LocalDate visitDate, String reason) {
        this.id = id;
        this.patientId = patientId;
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