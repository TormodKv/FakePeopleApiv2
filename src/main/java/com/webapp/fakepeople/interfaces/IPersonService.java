package com.webapp.fakepeople.interfaces;

import com.webapp.fakepeople.dto.PersonLinearDTO;
import com.webapp.fakepeople.dto.PersonTreeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public interface IPersonService {

    PersonLinearDTO getPersonLinearById(UUID id);

    PersonLinearDTO deleteById(UUID id);

    PersonLinearDTO create(PersonLinearDTO personLinearDTO);

    PersonLinearDTO edit(PersonLinearDTO personLinearDTO, UUID id);

    PersonTreeDTO getPersonTreeById(UUID id);

    ArrayList<PersonLinearDTO> getAll(Optional<Integer> limit, Optional<Boolean> alive);

    PersonLinearDTO replace(PersonLinearDTO personLinearDTO, UUID id);

    ArrayList<PersonLinearDTO> getPersonLinearCousinsById(UUID id);

    HashMap<String, Integer> getRankedLastNames();
}
