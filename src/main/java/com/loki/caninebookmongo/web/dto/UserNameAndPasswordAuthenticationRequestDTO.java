package com.loki.caninebookmongo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNameAndPasswordAuthenticationRequestDTO {

    @NotEmpty
    String username;
    @NotEmpty
    String password;


}
