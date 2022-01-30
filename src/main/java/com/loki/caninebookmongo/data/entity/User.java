package com.loki.caninebookmongo.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("USER")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    String userId;

    String firstName;

    String lastName;

    String userName;

    @JsonIgnore
    String password;

    String email;

    String phoneNumber;

    @DBRef(lazy = true)
    List<Pet> pets = new ArrayList<>();

    public User(String userId, String firstName, String lastName, String userName, String password, String email, String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
