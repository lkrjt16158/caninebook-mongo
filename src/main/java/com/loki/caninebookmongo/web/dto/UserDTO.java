package com.loki.caninebookmongo.web.dto;

import com.loki.caninebookmongo.data.entity.validation.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "First name should not be null or empty")
    String firstName;

    @NotEmpty(message = "Last name should not be null or empty")
    String lastName;

    @NotEmpty(message = "UserName should not be null or empty")
    String userName;

    @NotEmpty(message = "Password should not be null or empty")
    String password;

    @Email
    String email;

    @PhoneNumberValidator
    String phoneNumber;

}
