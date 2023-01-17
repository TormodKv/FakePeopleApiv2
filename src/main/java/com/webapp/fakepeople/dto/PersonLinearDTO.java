package com.webapp.fakepeople.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class PersonLinearDTO extends PersonDTO implements Serializable {
    private ArrayList<UUID> children;
    public ArrayList<UUID> getChildren() {
        return children;
    }
    public void setChildren(ArrayList<UUID> children) {
        this.children = children;
    }

}
