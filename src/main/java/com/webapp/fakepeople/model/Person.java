package com.webapp.fakepeople.model;

import com.webapp.fakepeople.annotations.Editable;
import com.webapp.fakepeople.annotations.NotNull;
import com.webapp.fakepeople.dto.PersonLinearDTO;
import com.webapp.fakepeople.dto.PersonTreeDTO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Person {
    @NotNull
    @Editable(setterMethod = "setFirstName", getterMethod = "getFirstName")
    private String firstName;
    @NotNull
    @Editable(setterMethod = "setLastName", getterMethod = "getLastName")
    private String lastName;
    @NotNull
    private final UUID id;
    private Person mother;
    private Person father;
    @Editable(setterMethod = "setChildren", getterMethod = "getChildren")
    private ArrayList<Person> children;
    @Editable(setterMethod = "setSpouse", getterMethod = "getSpouse")
    private Person spouse;
    @Editable(setterMethod = "setAddress", getterMethod = "getAddress")
    private Address address;
    private Calendar dateOfBirth;
    private Calendar dateOfDeath;
    @Editable(setterMethod = "setEmail", getterMethod = "getEmail")
    private String email;
    @NotNull
    private Gender gender;

    public Person() {
        id = UUID.randomUUID();
        children = new ArrayList<>();
    }

    public Person(String firstName, String lastName, UUID id, Person mother, Person father, ArrayList<Person> children, Person spouse, Address address, Calendar dateOfBirth, Calendar dateOfDeath, String email, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mother = mother;
        this.father = father;
        this.children = children;
        this.spouse = spouse;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.email = email;
        this.gender = gender;
        this.id = id == null ? UUID.randomUUID() : id;
    }

    public PersonLinearDTO toLinearDTO(){
        if (!personIsLegalObject(this)) return null;

        PersonLinearDTO dto = new PersonLinearDTO();

        dto.setAddress(this.address != null ? this.address.toAddressDTO() : null);
        dto.setDateOfBirth(this.dateOfBirth);
        dto.setEmail(this.email);
        dto.setGender(this.gender);
        dto.setDateOfDeath(this.dateOfDeath);
        dto.setId(this.id);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setSpouse(this.spouse != null ? this.spouse.id : null);
        dto.setFather(this.father != null ? this.father.id : null);
        dto.setMother(this.mother != null ? this.mother.id : null);
        dto.setChildren((ArrayList<UUID>) this.children.stream().map(Person::getId).collect(Collectors.toList()));

        return dto;
    }

    public PersonTreeDTO toTreeDTO(){
        if (!personIsLegalObject(this)) return null;

        PersonTreeDTO dto = new PersonTreeDTO();

        dto.setAddress(this.address != null ? this.address.toAddressDTO() : null);
        dto.setDateOfBirth(this.dateOfBirth);
        dto.setEmail(this.email);
        dto.setGender(this.gender);
        dto.setDateOfDeath(this.dateOfDeath);
        dto.setId(this.id);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setSpouse(this.spouse != null ? this.spouse.id : null);
        dto.setFather(this.father != null ? this.father.id : null);
        dto.setMother(this.mother != null ? this.mother.id : null);
        dto.setChildren((ArrayList<PersonTreeDTO>)this.children.stream().map(Person::toTreeDTO).collect(Collectors.toList()));

        return dto;
    }

    public static boolean personIsLegalObject(Person person){
        boolean emptyNotNullFields = false;
        for (Field field : person.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(NotNull.class)){
                try {
                    Object object = field.get(person);
                    if (object == null){
                        System.out.println(String.format("Required field: [%s] was found to be null", field));
                        emptyNotNullFields = true; //Using a variable to notify the user about every error
                    }
                } catch (Exception e) {
                    System.out.println("Couldn't get field from person. Error: + " + e);
                    return false;
                }
            }
        }
        return !emptyNotNullFields;
    }

    public int getAge(){
        Calendar calendar = Calendar.getInstance();
        return getAge(calendar);
    }

    public int getAge(Calendar atYear){
        long end = atYear.getTimeInMillis();
        long start = dateOfBirth.getTimeInMillis();
        return (int)TimeUnit.MILLISECONDS.toDays(Math.abs(end - start)) / 365;
    }

    public boolean isDead(){
        return dateOfDeath != null;
    }

    public boolean isAlive(){
        return !isDead();
    }
    public boolean isSingle(){
        return spouse == null;
    }
    public boolean isMarried(){
        return !isSingle();
    }


    public boolean livesAtParent(){
        if (mother != null && mother.address.getId() == address.getId()) return true;
        if (father != null && father.address.getId() == address.getId()) return true;
        return false;
    }

    public void addChild(Person child){
        if (child != null) children.add(child);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

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

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public ArrayList<Person> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Person> children) {
        if (children == null) return;
        this.children = children;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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
}

