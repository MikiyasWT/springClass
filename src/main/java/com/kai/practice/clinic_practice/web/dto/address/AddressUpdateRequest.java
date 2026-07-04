package com.kai.practice.clinic_practice.web.dto.address;

import jakarta.validation.constraints.NotBlank;

public class AddressUpdateRequest {

    @NotBlank(message = "line1 is required")
    private String line1;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "country is required")
    private String country;

    public AddressUpdateRequest() {
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