package com.loki.caninebookmongo.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("PET")
@NoArgsConstructor
public class Pet {

    @Id
    String petId;

    String petName;

    String breed;

    public Pet(String petName, String breed) {
        this.petName = petName;
        this.breed = breed;
    }
}
