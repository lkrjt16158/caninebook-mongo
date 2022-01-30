package com.loki.caninebookmongo.service.exceptions;

import lombok.Getter;

@Getter
public class PetAlreadyExistsException extends RuntimeException {
    private final String petName;
    private final String breed;

    public PetAlreadyExistsException(String petName, String breed) {
        super("Pet already exists with same name and breed");
        this.breed = breed;
        this.petName = petName;
    }


}
