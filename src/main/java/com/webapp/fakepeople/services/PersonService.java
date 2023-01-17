package com.webapp.fakepeople.services;

import com.webapp.fakepeople.dto.PersonLinearDTO;
import com.webapp.fakepeople.dto.PersonTreeDTO;
import com.webapp.fakepeople.interfaces.IAddressRepository;
import com.webapp.fakepeople.interfaces.IPersonFactory;
import com.webapp.fakepeople.interfaces.IPersonRepository;
import com.webapp.fakepeople.interfaces.IPersonService;
import com.webapp.fakepeople.annotations.Editable;
import com.webapp.fakepeople.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {

    private final IPersonFactory personFactory;
    private final IPersonRepository personRepository;
    private final IAddressRepository addressRepository;

    public PersonService(IPersonFactory personFactory, IPersonRepository personRepository, IAddressRepository addressRepository){
        this.personFactory = personFactory;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        personRepository.setPersons(personFactory.getAllPersons());
    }
    @Override
    public ArrayList<PersonLinearDTO> getAll(Optional<Integer> limit, Optional<Boolean> alive) {
        ArrayList<Person> persons = personRepository.getAll();
        return (ArrayList<PersonLinearDTO>) persons.stream()
                .filter(x -> alive.map(aBoolean -> x.isAlive() == aBoolean).orElse(true))
                .limit(limit.isPresent() ? limit.get() : Long.MAX_VALUE)
                .map(Person::toLinearDTO)
                .collect(Collectors.toList()); //Should be a safe cast because persons is always ArrayList
    }

    @Override
    public PersonLinearDTO replace(PersonLinearDTO personLinearDTO, UUID id) {
        Person legalPerson = createLegalPerson(personLinearDTO);
        if (legalPerson == null) return null;
        return personRepository.replace(legalPerson).toLinearDTO();
    }

    @Override
    public ArrayList<PersonLinearDTO> getPersonLinearCousinsById(UUID id) {
        Person originalPerson = personRepository.getById(id);
        ArrayList<PersonLinearDTO> cousins = new ArrayList<>();
        for (Person parent : Arrays.asList(originalPerson.getMother(), originalPerson.getFather())) {
            parent.getMother().getChildren()
                    .forEach(uncleOrAunt ->
                            uncleOrAunt.getChildren().forEach(cousin -> cousins.add(cousin.toLinearDTO()))
                    );
        }
        return cousins;
    }

    @Override
    public HashMap<String, Integer> getRankedLastNames() {
        HashMap<String, Integer> lastNames = new HashMap<>();
        for (Person person : personRepository.getAll()){
            lastNames.merge(person.getLastName(), 1, Integer::sum);
        }

        lastNames = lastNames.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return lastNames;
    }

    @Override
    public PersonLinearDTO getPersonLinearById(UUID id) throws HttpClientErrorException {
        try {
            return personRepository.getById(id).toLinearDTO();
        } catch (Exception e){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public PersonTreeDTO getPersonTreeById(UUID id) {
        return personRepository.getById(id).toTreeDTO();
    }

    @Override
    public PersonLinearDTO deleteById(UUID id) {
        return personRepository.deleteById(id).toLinearDTO();
    }

    @Override
    public PersonLinearDTO create(PersonLinearDTO personLinearDTO) {
        Person legalPerson = createLegalPerson(personLinearDTO);
        if (legalPerson == null) return null;
        return personRepository.create(legalPerson).toLinearDTO();
    }

    @Override
    public PersonLinearDTO edit(PersonLinearDTO personLinearDTO, UUID id) {
        Person originalPerson = personRepository.getById(id);
        if (originalPerson == null) return null;

        for (Field field : originalPerson.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(Editable.class)){
                try {
                    Editable editable = field.getAnnotation(Editable.class);
                    String capitalFieldName = field.toString();

                    Method personLinearDTOGetterMethod = personLinearDTO.getClass().getMethod(editable.getterMethod());
                    Method personSetterMethod = originalPerson.getClass().getMethod(editable.setterMethod(), field.getType());
                    Method personGetterMethod = originalPerson.getClass().getMethod(editable.getterMethod());

                    Object newValue = personLinearDTOGetterMethod.invoke(personLinearDTO);
                    Object oldValue = personGetterMethod.invoke(originalPerson);

                    if (newValue != null && oldValue != newValue){
                        personSetterMethod.invoke(originalPerson, newValue);
                    }

                } catch (Exception e) {
                    System.out.println("Couldn't edit person: " + e);
                    return null;
                }
            }
        }

        return personRepository.replace(originalPerson).toLinearDTO();
    }

    private Person createLegalPerson(PersonLinearDTO personLinearDTO){
        Person person = new Person(
                personLinearDTO.getFirstName(),
                personLinearDTO.getLastName(),
                personLinearDTO.getId(),
                personLinearDTO.getMother() != null ? personRepository.getById(personLinearDTO.getMother()) : null,
                personLinearDTO.getFather() != null ? personRepository.getById(personLinearDTO.getFather()) : null,
                new ArrayList<Person>(personLinearDTO.getChildren().stream().map(personRepository::getById).collect(Collectors.toList())),
                personLinearDTO.getSpouse() != null ? personRepository.getById(personLinearDTO.getSpouse()) : null,
                personLinearDTO.getAddress() != null ? addressRepository.getById(personLinearDTO.getAddress().getId()) : null,
                personLinearDTO.getDateOfBirth(),
                personLinearDTO.getDateOfDeath(),
                personLinearDTO.getEmail(),
                personLinearDTO.getGender()
        );

        if (!Person.personIsLegalObject(person)) return null;
        return person;
    }

}
