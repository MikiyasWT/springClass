package com.kai.practice.clinic_practice.model;

public class Patient {

    public Patient() {}

    private String id;
    private String givenName;
    private String familyName;


    public Patient(String id, String givenName, String familyName) {
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

    public String getGivenName () {
       return givenName;
    }

    public void setGivenName(String givenName){
        this.givenName = givenName;
    }

    public String getFamilyName () {
        return familyName;
    }

    public void setFamilyName(String familName) {
        this.familyName = familyName;
    }

}