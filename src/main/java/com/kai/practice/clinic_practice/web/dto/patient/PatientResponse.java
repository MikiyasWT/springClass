package com.kai.practice.clinic_practice.web.dto.patient;

public class PatientResponse {

    private String id;
    private String givenName;
    private String familyName;

    public PatientResponse () { }

    public PatientResponse (String id, String givenName, String familyName) {
       this.id = id;
       this.givenName = givenName;
       this.familyName = familyName;
    }


    public String getId() {
         return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFamilyName () {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName () {
      return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }


}