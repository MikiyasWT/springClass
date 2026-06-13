package com.kai.practice.clinic_practice.web.dto.visit;


import com.kai.practice.clinic_practice.model.Visit;



public final class VisitMapper {

    private VisitMapper() {
    }


  public static Visit toEntity(VisitCreateRequest request) {
      return new Visit(
        request.getId(),
        request.getPatientId(),
        request.getVisitDate(),
        request.getReason()
      );
  }

  public static Visit toEntity(String id, VisitUpdateRequest request) {
        return new Visit(
            id,
            request.getPatientId(),
            request.getVisitDate(),
            request.getReason()
        );
  }

  public static VisitResponse toResponse(Visit visit) {
        return new VisitResponse(
            visit.getId(),
            visit.getPatientId(),
            visit.getVisitDate(),
            visit.getReason()
        );
  }

}