package com.kai.practice.clinic_practice.web.dto.address;

import com.kai.practice.clinic_practice.web.dto.address.*;
import com.kai.practice.clinic_practice.model.Address;

public final class  AddressMapper {

    private AddressMapper () {

    }

    public static Address toEntity(AddressCreateRequest request) {
        Address address = new Address();
        address.setId(request.getId());
        address.setLine1(request.getLine1());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        return address;
    }

    public static Address toEntity(String addressId, AddressUpdateRequest request) {
        Address address = new Address();
        address.setId(addressId);
        address.setLine1(request.getLine1());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        return address;
    }

    public static AddressResponse toResponse(Address address) {
        return new AddressResponse(
                  address.getId(),
                  address.getPatientId(),
                  address.getLine1(),
                  address.getCity(),
                  address.getCountry()
        );
    }
}