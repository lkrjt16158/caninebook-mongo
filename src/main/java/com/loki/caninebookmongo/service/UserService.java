package com.loki.caninebookmongo.service;

import com.loki.caninebookmongo.data.entity.User;

public interface UserService {

    boolean userExistsByUserName(String userName);

    boolean userExistsByPhoneNumber(String phoneNumber);

    boolean userExistsByEmail(String email);

}
