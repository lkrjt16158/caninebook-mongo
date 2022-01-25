package com.loki.caninebookmongo.data.entity;


import com.loki.caninebookmongo.data.entity.validation.PhoneNumberValidator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Document("USER")
public class User {

    @Id
    String userId;

    String firstName;

    String lastName;

    String userName;

    String password;

    String email;

    String phoneNumber;

}
