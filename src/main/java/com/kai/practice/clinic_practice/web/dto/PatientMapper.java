package com.kai.practice.clinic_practice.web.dto;

import com.kai.practice.clinic_practice.model.Patient;

public final class PatientMapper {

    private PatientMapper () { }

    public static Patient toEntity(PatientCreateRequest request) {
      return new Patient(
                    request.getId(),
                    request.getGivenName(),
                    request.getFamilyName()
      );
    }

    public static Patient toEntity(String id, PatientUpdateRequest request) {
      return new Patient(
                    id,
                    request.getGivenName(),
                    request.getFamilyName()
      );
    }

   public static PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getGivenName(),
                patient.getFamilyName()
        );
    }
}