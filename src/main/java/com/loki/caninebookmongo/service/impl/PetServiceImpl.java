package com.loki.caninebookmongo.service.impl;

import com.loki.caninebookmongo.data.entity.Pet;
import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.data.repository.PetRepository;
import com.loki.caninebookmongo.data.repository.UserRepository;
import com.loki.caninebookmongo.service.PetService;
import com.loki.caninebookmongo.service.exceptions.UserInvalidException;
import com.loki.caninebookmongo.web.dto.PetDTO;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public PetServiceImpl(UserRepository userRepository, PetRepository petRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    @Override
    public User addPet(PetDTO petDTO, String userName) {

        Pet pet = new Pet(petDTO.getPetName(), petDTO.getBreed());
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserInvalidException("No such user exists"));
        pet = petRepository.save(pet);
        user.getPets().add(pet);

        return userRepository.save(user);
    }
}
