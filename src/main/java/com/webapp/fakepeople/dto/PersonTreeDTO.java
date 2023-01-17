package com.webapp.fakepeople.dto;

import com.webapp.fakepeople.model.Address;
import com.webapp.fakepeople.model.Gender;
import com.webapp.fakepeople.model.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class PersonTreeDTO extends PersonDTO implements Serializable {
    private ArrayList<PersonTreeDTO> children;
    public ArrayList<PersonTreeDTO> getChildren() {
        return children;
    }
    public void setChildren(ArrayList<PersonTreeDTO> children) {
        this.children = children;
    }

}

