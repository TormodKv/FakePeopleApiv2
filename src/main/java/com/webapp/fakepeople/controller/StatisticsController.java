package com.webapp.fakepeople.controller;

import com.webapp.fakepeople.interfaces.IStatisticsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final IStatisticsService statisticsServiceService;

    public StatisticsController(IStatisticsService statisticsServiceService) {
        this.statisticsServiceService = statisticsServiceService;
    }

    @GetMapping("/lastnames")
    public Map<String, Integer> GetRankedLastNames(){
        return statisticsServiceService.getRankedLastNames();
    }

    @GetMapping("/genders")
    public Map<String, Integer> GetRankedGenders(){
        return statisticsServiceService.getRankedGenders();
    }

    @GetMapping("/population")
    public Map<String, Integer> GetPopulationPerYear(){
        return statisticsServiceService.getPopulationPerYear();
    }

    @GetMapping("/population/{year}")
    public Map<String, Integer> GetPopulationForYear(@PathVariable int year){
        return statisticsServiceService.getPopulationForYear(year);
    }

    @GetMapping("/births")
    public Map<String, Integer> GetBirthsPerYear(){
        return statisticsServiceService.getBirthsPerYear();
    }

    @GetMapping("/births/{year}")
    public Map<String, Integer> GetBirthsForYear(@PathVariable int year){
        return statisticsServiceService.getBirthsForYear(year);
    }
}
