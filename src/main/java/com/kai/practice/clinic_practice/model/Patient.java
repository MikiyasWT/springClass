package com.kai.practice.clinic_practice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;


@Entity
@Table(name = "patients")
public class Patient {

    public Patient() {}
    @Id
    private String id;

    private String givenName;
    private String familyName;
     
    @OneToMany(mappedBy = "patient")
    private List<Visit> visits;

    @OneToOne(mappedBy = "patient")
    private Address address;



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

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public Address getAddress(){
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}