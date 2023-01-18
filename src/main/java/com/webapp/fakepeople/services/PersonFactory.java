package com.webapp.fakepeople.services;

import com.webapp.fakepeople.interfaces.IAddressRepository;
import com.webapp.fakepeople.interfaces.IPersonFactory;
import com.webapp.fakepeople.model.Address;
import com.webapp.fakepeople.model.Family;
import com.webapp.fakepeople.model.Gender;
import com.webapp.fakepeople.model.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.webapp.fakepeople.repository.Names.*;

@Component
public class PersonFactory implements IPersonFactory {

    private IAddressRepository addressRepository;
    private static final int MAX_DEATH_AGE = 100;
    private static final int MIN_ADULT_AGE = 18;
    private static final int MAX_MARRIAGE_AGE_DIFF = 5;
    private static final int DEFAULT_NUMBER_OF_PERSONS = 2000;
    private static final int DEFAULT_START_YEAR = 1900;
    private final ArrayList<Person> singlePersons = new ArrayList<>();
    private final ArrayList<Person> deadPersons = new ArrayList<>();
    private final ArrayList<Family> families = new ArrayList<>();
    private ArrayList<Address> addresses;

    public PersonFactory(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    private ArrayList<Person> generatePersons(int numberOfPersons, int startYear) {

        fillAddresses();

        //Creating initial persons
        for (int i = 0; i < numberOfPersons; i++){
            Person person = new Person();
            person.setAddress(getRandomAddress());
            person.setGender(ThreadLocalRandom.current().nextBoolean() ? Gender.male : Gender.female);
            person.setFirstName(getRandomFirstName(person.getGender()));
            person.setLastName(getRandomLastName());

            int birthYear = ThreadLocalRandom.current().nextInt(startYear - MAX_DEATH_AGE, startYear);
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.set(birthYear, 1, 1);
            person.setDateOfBirth(birthCalendar);

            person.setEmail(getRandomEmailAddress(person));

            singlePersons.add(person);
        }

        //Emulate years
        for (int year = startYear; year <= Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin")).get(Calendar.YEAR); year++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, 1, 1);

            ArrayList<Person> toRemoveFromSinglePersons = new ArrayList<>();

            for (Person person : singlePersons) {
                if (person.getAge(calendar) > MIN_ADULT_AGE && person.getAge(calendar) < MAX_DEATH_AGE) {
                    if (person.isSingle() && percentChance(10)) {

                        //Marry a person
                        Person spouse = getFittingSpouse(person);
                        if (spouse != null){
                            Family family = marriage(person, spouse);
                            families.add(family);
                            toRemoveFromSinglePersons.add(person);
                            toRemoveFromSinglePersons.add(spouse);
                        }

                    } else if (percentChance(10)) {

                        //Move
                        Address oldAddress = person.getAddress();
                        person.setAddress(getRandomAddress());
                        addresses.add(oldAddress);

                    } else if (percentChance(1)) {

                        //Die
                        person.setDateOfDeath(calendar);
                        toRemoveFromSinglePersons.add(person);
                        deadPersons.add(person);
                    }
                } else if (person.getAge(calendar) >= MAX_DEATH_AGE) {

                    //Die
                    person.setDateOfDeath(calendar);
                    toRemoveFromSinglePersons.add(person);
                    deadPersons.add(person);
                }
            }

            toRemoveFromSinglePersons.forEach(x -> singlePersons.remove(x));

            ArrayList<Family> toRemoveFromFamilies = new ArrayList<>();

            for (Family family : families) {

                if (family.mother().isAlive() && family.father().isAlive() && percentChance(15)){

                    //Get child
                    Person child = new Person();
                    child.setAddress(family.father().getAddress());
                    child.setGender(percentChance(50) ? Gender.male : Gender.female);
                    child.setFirstName(getRandomFirstName(child.getGender()));
                    child.setLastName(family.father().getLastName());
                    child.setDateOfBirth(calendar);
                    child.setEmail(getRandomEmailAddress(child));

                    family.GetChild(child);
                }
                //for (Person child : family.getChildren()){
                for (int i = family.getChildren().size()-1; i >= 0; i--){
                    Person child = family.getChildren().get(i);
                    if (child.getAge(calendar) >= MIN_ADULT_AGE && percentChance(10)){
                        family.childMoveOut(child);
                        child.setAddress(getRandomAddress());
                        singlePersons.add(child);
                    }
                }
                for (Person parent : Arrays.asList(family.father(), family.mother())){
                    if (parent.getAge(calendar) >= MAX_DEATH_AGE || percentChance(2)){
                        //Die
                        parent.setDateOfDeath(calendar);
                        deadPersons.add(parent);
                    }
                }

                //Parents are dead
                if (family.father().isDead() && family.mother().isDead()){
                    while (family.getChildren().size() > 0){
                        Person child = family.getChildren().get(0);
                        family.childMoveOut(child);
                        child.setAddress(getRandomAddress());
                        singlePersons.add(child);

                        deadPersons.add(family.father());
                        deadPersons.add(family.mother());
                        toRemoveFromFamilies.add(family);
                    }
                }
            }

            toRemoveFromFamilies.forEach(families::remove);
        }

        return null;
    }

    private Address getRandomAddress(){
        return addresses.remove(ThreadLocalRandom.current().nextInt(0, addresses.size()));
    }

    private String getRandomEmailProvider(){
        return emailProviderNames[ThreadLocalRandom.current().nextInt(0, emailProviderNames.length)];
    }

    private String getRandomFirstName(Gender gender){
        return gender == Gender.female ?
                femaleNames[ThreadLocalRandom.current().nextInt(0, femaleNames.length)] :
                maleNames[ThreadLocalRandom.current().nextInt(0, maleNames.length)];
    }

    private String getRandomEmailAddress (Person person){
        return person.getFirstName().
                toLowerCase().
                concat(Integer.toString(person.getDateOfBirth().get(Calendar.YEAR)).substring(2)).
                concat("@").concat(getRandomEmailProvider()).
                concat(".com");
    }

    private String getRandomLastName(){
        return lastNameBeginnings[ThreadLocalRandom.current().nextInt(0, lastNameBeginnings.length)]
                .concat(lastNameEndings[ThreadLocalRandom.current().nextInt(0, lastNameEndings.length)]);
    }

    private Person getFittingSpouse(Person person){
        return singlePersons.stream().
                filter(x -> x.isSingle() && x.isAlive() && x.getGender() != person.getGender() && Math.max(person.getAge(), x.getAge()) - Math.min(person.getAge(), x.getAge()) <= MAX_MARRIAGE_AGE_DIFF).
                findAny().orElse(null);
    }

    private Family marriage(Person person1, Person person2){

        Person male = person1.getGender() == Gender.male ? person1 : person2;
        Person female = person1.getGender() == Gender.female ? person1 : person2;

        //Move in together
        if (!male.livesAtParent()) female.setAddress(male.getAddress());
        else if (!female.livesAtParent()) male.setAddress(female.getAddress());
        else {
            Address newAddress = getRandomAddress();
            female.setAddress(newAddress);
            male.setAddress(newAddress);
        }
        return new Family(male, female);
    }

    @Override
    public ArrayList<Person> getAllPersons(){
        if (singlePersons.size() + deadPersons.size() == 0){
            generatePersons(DEFAULT_NUMBER_OF_PERSONS, DEFAULT_START_YEAR);
        }
        singlePersons.addAll(deadPersons);
        families.forEach(x -> {
            singlePersons.addAll(x.getChildren());
            if (x.mother().isAlive()) singlePersons.add(x.mother());
            if (x.father().isAlive()) singlePersons.add(x.father());
        });
        return singlePersons;
    }

    //Returns true 60% of the time when percentage arguments is set to 60 😉
    public static boolean percentChance(int percentage){
        return ThreadLocalRandom.current().nextFloat() * 100 < percentage;
    }

    private void fillAddresses(){
        try {
            addresses = new AddressReader().readLines(false); //TODO maybe use dependency injection for AddressReader?
        } catch (IOException e) {
            System.out.println("Couldn't create persons. Address file couldn't be read: " + e);
            return;
        }

        addressRepository.addAllAddresses(addresses);

        Collections.shuffle(addresses);
    }
}
