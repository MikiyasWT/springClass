package com.kai.practice.clinic_practice.web.dto.provider;

import jakarta.validation.constraints.NotBlank;


public class ProviderUpdateRequest {

    @NotBlank(message = "givenName is required")
    private String givenName;

    @NotBlank(message = "familyName is required")
    private String familyName;

    @NotBlank(message = "Specialty is required")
    private String specialty;


  
    public ProviderUpdateRequest() { }

    public ProviderUpdateRequest(String givenName, String familyName, String specialty) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.specialty = specialty;
    }


     public String getGivenName(){
        return givenName;
     }

     public void setGivenName(String givenName){
        this.givenName = givenName;
     }

     public String getFamilyName() {
        return familyName;
     }

     public void setFamilyName(String familyName){
        this.familyName = familyName;
     }

     public String getSpecialty() {
        return specialty;
     }

    public void setSpecialty(String specialty){
        this.specialty = specialty;
     }

}