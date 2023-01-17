package com.webapp.fakepeople.model;

import java.util.ArrayList;
import java.util.Collections;

public record Family(Person father, Person mother) {
    public Family(Person father, Person mother) {
        this.father = father;
        this.mother = mother;
        father.setSpouse(mother);
        mother.setSpouse(father);
        mother.setLastName(father.getLastName());
    }

    public void GetChild(Person child) {
        child.setFather(father);
        child.setMother(mother);
        father.addChild(child);
        mother.addChild(child);
    }

    public void moveToAddress(Address address) {
        father.setAddress(address);
        mother.setAddress(address);
    }

    public ArrayList<Person> getChildren(){
        return father.getChildren();
    }

    public void childMoveOut(Person child){
        father.getChildren().remove(child);
        mother.getChildren().remove(child);
    }
}
