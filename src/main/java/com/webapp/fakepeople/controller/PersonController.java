package com.webapp.fakepeople.controller;

import com.webapp.fakepeople.dto.PersonLinearDTO;
import com.webapp.fakepeople.dto.PersonTreeDTO;
import com.webapp.fakepeople.interfaces.IPersonService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final IPersonService personService;
    public PersonController(IPersonService personService){
        this.personService = personService;
    }

    @GetMapping
    public @ResponseBody ArrayList<PersonLinearDTO> GetAll(@RequestParam Optional<Integer> limit, Optional<Boolean> alive){
        return personService.getAll(limit, alive);
    }

    @GetMapping("/rankedLastNames")
    public HashMap<String, Integer> GetRankedLastNames(){
        return personService.getRankedLastNames();
    }

    @GetMapping("/{id}")
    public @ResponseBody PersonLinearDTO GetPersonLinearById(@PathVariable UUID id){
        return personService.getPersonLinearById(id);
    }

    @GetMapping("/{id}/cousins")
    public @ResponseBody ArrayList<PersonLinearDTO> GetPersonLinearCousinsById(@PathVariable UUID id){
        return personService.getPersonLinearCousinsById(id);
    }
    @GetMapping("/{id}/family")
    public @ResponseBody PersonTreeDTO GetPersonTreeById(@PathVariable UUID id){
        return personService.getPersonTreeById(id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody PersonLinearDTO DeleteById(@PathVariable UUID id){
        return personService.deleteById(id);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PersonLinearDTO Create(@RequestBody PersonLinearDTO personLinearDTO){
        return personService.create(personLinearDTO);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PersonLinearDTO Edit(@RequestBody PersonLinearDTO personLinearDTO, @PathVariable UUID id){
        return personService.edit(personLinearDTO, id);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PersonLinearDTO Replace(@RequestBody PersonLinearDTO personLinearDTO, @PathVariable UUID id){
        return personService.replace(personLinearDTO, id);
    }
}
