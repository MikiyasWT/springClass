package com.kai.practice.clinic_practice.web.dto;

import jakarta.validation.constraints.NotBlank;

public class PatientUpdateRequest {

    @NotBlank(message = "givenName can't be empty")
    private String givenName;

    @NotBlank(message = "familyName can't be empty")
    private String familyName;

    public PatientUpdateRequest () {}

    public PatientUpdateRequest (String givenName, String familyName) {
     this.givenName = givenName;
     this.familyName = familyName;
    }

    public String getGivenName () {
       return givenName;
    }

    public void setGivenName (String givenName) {
      this.givenName = givenName;
    }

    public String getFamilyName () {
        return familyName;
    }
    
    public void setFamilyName (String familyName) {
        this.familyName = familyName;
    }

}