package com.webapp.fakepeople.services;

import com.webapp.fakepeople.interfaces.IPersonRepository;
import com.webapp.fakepeople.interfaces.IStatisticsService;
import com.webapp.fakepeople.model.Person;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService implements IStatisticsService {

    private final IPersonRepository personRepository;

    public StatisticsService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public HashMap<String, Integer> getRankedLastNames() {
        HashMap<String, Integer> lastNames = new HashMap<>();
        for (Person person : personRepository.getAll()){
            lastNames.merge(person.getLastName(), 1, Integer::sum);
        }

        return sortHashMapByValue(lastNames, true);
    }

    @Override
    public HashMap<String, Integer> getRankedGenders() {
        HashMap<String, Integer> genders = new HashMap<>();
        for (Person person : personRepository.getAll()){
            genders.merge(person.getGender().toString(), 1, Integer::sum);
        }

        return sortHashMapByValue(genders, true);
    }

    @Override
    public TreeMap<String, Integer> getPopulationPerYear() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        HashMap<String, Integer> years = new HashMap<>();
        personRepository.getAll().forEach(x -> {
            int startYear = x.getDateOfBirth().get(Calendar.YEAR);
            int endYear = x.isDead() ? x.getDateOfDeath().get(Calendar.YEAR) : currentYear;
            for (int year = startYear; year <= endYear; year++) {
                years.merge(String.valueOf(year), 1, Integer::sum);
            }
        });
        return sortHashMapByKey(years);
    }

    @Override
    public HashMap<String, Integer> getPopulationForYear(int year) {
        HashMap<String, Integer> years = new HashMap<>();
        int count = (int) personRepository.getAll().stream().filter(x ->
            (x.getDateOfBirth().get(Calendar.YEAR) <= year && (x.isAlive() || x.getDateOfDeath().get(Calendar.YEAR) >= year))
        ).count();
        years.put(String.valueOf(year), count);
        return years;
    }

    @Override
    public TreeMap<String, Integer> getBirthsPerYear() {
        HashMap<String, Integer> years = new HashMap<>();
        for (Person person : personRepository.getAll()){
            years.merge(String.valueOf(person.getDateOfBirth().get(Calendar.YEAR)), 1, Integer::sum);
        }
        return sortHashMapByKey(years);
    }

    @Override
    public HashMap<String, Integer> getBirthsForYear(int year) {
        HashMap<String, Integer> years = new HashMap<>();
        int count = (int) personRepository.getAll().stream()
                .filter(x -> x.getDateOfBirth().get(Calendar.YEAR) == year).count();
        years.put(String.valueOf(year), count);
        return years;
    }

    private HashMap<String, Integer> sortHashMapByValue(HashMap<String, Integer> hashMap, boolean invert){
        return hashMap.entrySet().stream()
                .sorted(invert ? Map.Entry.comparingByValue(Comparator.reverseOrder()) : Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private TreeMap<String, Integer> sortHashMapByKey(HashMap<String, Integer> hashMap){
        return new TreeMap<>(hashMap);
    }
}
