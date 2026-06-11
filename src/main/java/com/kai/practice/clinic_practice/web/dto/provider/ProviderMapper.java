package com.kai.practice.clinic_practice.web.dto.provider;

import com.kai.practice.clinic_practice.model.Provider;
import com.kai.practice.clinic_practice.web.dto.provider.ProviderCreateRequest;
import com.kai.practice.clinic_practice.web.dto.provider.ProviderUpdateRequest;


public final class ProviderMapper {

    private ProviderMapper() {

    }

    public static Provider toEntity(ProviderCreateRequest request) {
       return new Provider(
         request.getId(),
         request.getGivenName(),
         request.getFamilyName(),
         request.getSpecialty()
       );
    }


    public static Provider toEntity(String id, ProviderUpdateRequest request) {
        return new Provider(
            id,
            request.getGivenName(),
            request.getFamilyName(),
            request.getSpecialty()
        );
    }


    public static ProviderResponse toResponse(Provider provider) {
     return new ProviderResponse(
                provider.getId(),
                provider.getGivenName(),
                provider.getFamilyName(),
                provider.getSpecialty()
        );
    }
}