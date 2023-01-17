package com.webapp.fakepeople.interfaces;

import com.webapp.fakepeople.model.Person;

import java.io.IOException;
import java.util.ArrayList;

public interface IPersonFactory {
    ArrayList<Person> getAllPersons();
}
