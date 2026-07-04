package com.kai.practice.clinic_practice.web.dto.address;

import jakarta.validation.constraints.*;

public class AddressCreateRequest {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "line1 is required")
    private String line1;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "country is required")
    private String country;


    public AddressCreateRequest() { }

    public AddressCreateRequest(String id,String line1, String city, String country) {
        this.id = id;
        this.line1 = line1;
        this.city = city;
        this.country = country;
    }


    public void setId(String id) {
         this.id = id;
    }

    public String getId() {
        return id;
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
}