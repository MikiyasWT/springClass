package com.kai.practice.clinic_practice.web.dto.patient;

import jakarta.validation.constraints.NotBlank;

public class PatientCreateRequest {

   @NotBlank(message = "id is required")
   private String id;

   @NotBlank(message = "givenName is required")
   private String givenName;

   @NotBlank(message = "familyName is required")
   private String familyName;

   public PatientCreateRequest () { }

   public PatientCreateRequest (String id, String familyName, String givenName) { 
        this.id = id;
        this.familyName = familyName;
        this.givenName = givenName;
    }

   public String getId() {
    return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getGivenName() {
    return givenName;
   }

   public void setGivenName(String givenName) {
      this.givenName = givenName;
   }

   String getFamilyName() {
    return familyName;
   }

   public void setFamilyName(String familyName) {
      this.familyName = familyName;
   }
}