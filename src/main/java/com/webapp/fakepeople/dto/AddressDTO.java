package com.webapp.fakepeople.dto;

import java.util.UUID;

public class AddressDTO {
    private UUID id;
    private String addressText;
    private int houseNumber;
    private String houseLetter;
    private int zipCode;
    private String municipality;
    private int municipalityNumber;

    public AddressDTO(UUID id, String addressText, int houseNumber, String houseLetter, int zipCode, String municipality, int municipalityNumber) {
        this.id = id;
        this.addressText = addressText;
        this.houseNumber = houseNumber;
        this.houseLetter = houseLetter;
        this.zipCode = zipCode;
        this.municipality = municipality;
        this.municipalityNumber = municipalityNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseLetter() {
        return houseLetter;
    }

    public void setHouseLetter(String houseLetter) {
        this.houseLetter = houseLetter;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public int getMunicipalityNumber() {
        return municipalityNumber;
    }

    public void setMunicipalityNumber(int municipalityNumber) {
        this.municipalityNumber = municipalityNumber;
    }
}
