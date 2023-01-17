package com.webapp.fakepeople.interfaces;

import com.webapp.fakepeople.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public interface IPersonRepository {

    ArrayList<Person> getAll();
    Person getById(UUID id);
    Person deleteById(UUID id);
    Person replace(Person person);
    Person create(Person person);
    ArrayList<Person> setPersons(ArrayList<Person> persons);
}
