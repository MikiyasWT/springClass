package com.kai.practice.clinic_practice.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;


@Entity
@Table(name = "providers")
public class Provider {

    public Provider () { }

    @Id
    private String id;
    private String givenName;
    private String familyName;
    private String specialty;

    public Provider (String id, String givenName, String familyName, String specialty) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.specialty = specialty;
     }

     public String getId() {
        return id;
     }

     public void setId(String id){
        this.id = id;
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