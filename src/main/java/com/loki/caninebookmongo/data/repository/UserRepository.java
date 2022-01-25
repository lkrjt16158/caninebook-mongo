package com.loki.caninebookmongo.data.repository;

import com.loki.caninebookmongo.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {


    boolean existsByUserName(String userName);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
