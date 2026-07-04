
package com.kai.practice.clinic_practice.model;

import jakarta.persistence.*;



@Entity
@Table(name = "addressess")
public class Address {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String line1;

    private String city;

    private String country;

    public Address() {

    }

    public void setId(String id) {
         this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setPatient(Patient patient) {
         this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setLine1(String line1) {
         this.line1 = line1;
    }

    public String getLine1() {
        return line1;
    }

    public void setCity(String city) {
         this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCountry(String country) {
         this.country = country;
    }

    public String getCountry() {
        return country;
    }


    public String getPatientId() {
       return patient != null ? patient.getId() : null;
    }

     
}