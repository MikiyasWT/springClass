package com.kai.practice.clinic_practice.web.dto.visit;


import com.kai.practice.clinic_practice.model.Visit;



public final class VisitMapper {

    private VisitMapper() {
    }


    public static Visit toEntity(VisitCreateRequest request) {
      Visit visit = new Visit();
      visit.setId(request.getId());
      visit.setVisitDate(request.getVisitDate());
      visit.setReason(request.getReason());
      return visit;
    }

    public static Visit toEntity(String id, VisitUpdateRequest request) {
      Visit visit = new Visit();
      visit.setId(id);
      visit.setVisitDate(request.getVisitDate());
      visit.setReason(request.getReason());
      return visit;
    }

    public static VisitResponse toResponse(Visit visit) {
          return new VisitResponse(
              visit.getId(),
              visit.getPatientId(),
              visit.getProviderId(),
              visit.getVisitDate(),
              visit.getReason()
          );
    }

}