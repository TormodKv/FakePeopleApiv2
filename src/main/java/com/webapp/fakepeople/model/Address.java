package com.webapp.fakepeople.model;

import java.util.Objects;
import java.util.UUID;

import com.opencsv.bean.CsvBindByName;
import com.webapp.fakepeople.dto.AddressDTO;

public class Address {

    private final UUID id;
    @CsvBindByName(column = "adresseTekst")
    private String addressText;
    @CsvBindByName(column = "nummer")
    private int houseNumber;
    @CsvBindByName(column = "bokstav")
    private String houseLetter;
    @CsvBindByName(column = "postnummer")
    private int zipCode;
    @CsvBindByName(column = "kommunenavn")
    private String municipality;
    @CsvBindByName(column = "kommunenummer")
    private int municipalityNumber;

    public Address(){
        this.id = UUID.randomUUID();
    }

    public AddressDTO toAddressDTO(){
        return new AddressDTO(
        this.id,
        this.addressText,
        this.houseNumber,
        this.houseLetter,
        this.zipCode,
        this.municipality,
        this.municipalityNumber
        );
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressText='" + addressText + '\'' +
                ", houseNumber=" + houseNumber +
                ", houseLetter='" + houseLetter + '\'' +
                ", zipCode=" + zipCode +
                ", municipality='" + municipality + '\'' +
                ", municipalityNumber=" + municipalityNumber +
                '}';
    }

    public UUID getId() {
        return id;
    }
    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        if (addressText.length() == 0) return;
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
        if (houseLetter.length() == 0) return;
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
        if (municipality.length() == 0) return;
        this.municipality = municipality;
    }

    public int getMunicipalityNumber() {
        return municipalityNumber;
    }

    public void setMunicipalityNumber(int municipalityNumber) {
        this.municipalityNumber = municipalityNumber;
    }
}
