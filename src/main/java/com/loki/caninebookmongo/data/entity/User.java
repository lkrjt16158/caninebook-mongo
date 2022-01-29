package com.loki.caninebookmongo.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loki.caninebookmongo.data.entity.validation.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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

}
