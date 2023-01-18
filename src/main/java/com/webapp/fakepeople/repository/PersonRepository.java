package com.webapp.fakepeople.repository;

import com.webapp.fakepeople.interfaces.IPersonRepository;
import com.webapp.fakepeople.model.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class PersonRepository implements IPersonRepository {

    private final ArrayList<Person> persons = new ArrayList<>();

    @Override
    public ArrayList<Person> getAll() {
        return persons;
    }

    @Override
    @Cacheable(cacheNames = "persons", key = "#id")
    public Person getById(UUID id) {
        return persons.stream()
                .filter(x -> x.getId().compareTo(id) == 0)
                .findAny()
                .orElse(null);
    }

    @Override
    @CacheEvict(cacheNames = "persons", key = "#id")
    public Person deleteById(UUID id) {
        for (int i = 0; i < persons.size(); i++){
            if (persons.get(i).getId().compareTo(id) == 0) return persons.remove(i);
        }
        return null;
    }

    @Override
    @CachePut(cacheNames = "persons", key = "#person.getId()") //TODO check if getId() works annotation
    public Person replace(Person person) {
        deleteById(person.getId());
        return create(person);
    }

    @Override
    @CachePut(cacheNames = "persons", key = "#person.getId()")
    public Person create(Person person) {
        persons.add(person);
        return person;
    }

    @Override
    public ArrayList<Person> setPersons(ArrayList<Person> persons) {
        this.persons.addAll(persons);
        return this.persons;
    }
}
