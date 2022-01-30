package com.loki.caninebookmongo.web.controller;

import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.security.AuthenticationFailureException;
import com.loki.caninebookmongo.service.PetService;
import com.loki.caninebookmongo.web.dto.PetDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/add-pet")
    public ResponseEntity<User> addPet(@RequestBody @Valid PetDTO petDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            User user = petService.addPet(petDTO, authentication.getName());
            return ResponseEntity.ok(user);
        }

        throw new AuthenticationFailureException("Invalid Access");
    }
}
