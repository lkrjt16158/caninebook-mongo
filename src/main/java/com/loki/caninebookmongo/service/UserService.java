package com.loki.caninebookmongo.service;

import com.loki.caninebookmongo.data.entity.User;

import java.sql.Struct;

public interface UserService {

    boolean userExistsByUserName(String userName);

    boolean userExistsByPhoneNumber(String phoneNumber);

    boolean userExistsByEmail(String email);

    User userIsValid(String value);
}
