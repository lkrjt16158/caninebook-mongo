package com.loki.caninebookmongo.service;

import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.web.dto.PetDTO;

public interface PetService {

    User addPet(PetDTO petDTO, String userName);
}
