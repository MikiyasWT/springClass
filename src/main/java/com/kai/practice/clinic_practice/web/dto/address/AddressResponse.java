package com.kai.practice.clinic_practice.web.dto.address;

public class AddressResponse {

    private String id;
    private String patientId;
    private String line1;
    private String city;
    private String country;

    public AddressResponse() {
    }

    public AddressResponse(String id, String patientId, String line1, String city, String country) {
        this.id = id;
        this.patientId = patientId;
        this.line1 = line1;
        this.city = city;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}