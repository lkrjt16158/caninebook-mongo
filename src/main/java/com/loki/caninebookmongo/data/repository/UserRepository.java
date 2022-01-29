package com.loki.caninebookmongo.data.repository;

import com.loki.caninebookmongo.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {


    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String userName);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String value);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    void deleteByUserName(String userName);


}
