package com.loki.caninebookmongo.data.repository;

import com.loki.caninebookmongo.data.entity.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetRepository extends MongoRepository<Pet, Long> {
}
