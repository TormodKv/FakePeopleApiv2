package com.webapp.fakepeople.interfaces;

import com.webapp.fakepeople.model.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public interface IPersonFactory {
    ArrayList<Person> getAllPersons();
}
