package com.webapp.fakepeople.interfaces;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.TreeMap;

@Component
public interface IStatisticsService {

    HashMap<String, Integer> getRankedLastNames();

    HashMap<String, Integer> getRankedGenders();

    TreeMap<String, Integer> getPopulationPerYear();

    HashMap<String, Integer> getPopulationForYear(int year);

    TreeMap<String, Integer> getBirthsPerYear();

    HashMap<String, Integer> getBirthsForYear(int year);
}
