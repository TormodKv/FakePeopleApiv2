package com.webapp.fakepeople.dto;

import com.webapp.fakepeople.model.Address;
import com.webapp.fakepeople.model.Gender;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public abstract class PersonDTO implements Serializable {
    private String firstName;
    private String lastName;
    private UUID id;
    private UUID mother;
    private UUID father;
    private UUID spouse;
    private AddressDTO address;
    private Calendar dateOfBirth;
    private Calendar dateOfDeath;
    private String email;
    private Gender gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMother() {
        return mother;
    }

    public void setMother(UUID mother) {
        this.mother = mother;
    }

    public UUID getFather() {
        return father;
    }

    public void setFather(UUID father) {
        this.father = father;
    }

    public UUID getSpouse() {
        return spouse;
    }

    public void setSpouse(UUID spouse) {
        this.spouse = spouse;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Calendar getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Calendar dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
