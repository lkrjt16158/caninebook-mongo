package com.loki.caninebookmongo.service;

import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.web.dto.UserDTO;

public interface UserRegistrationService {
    User registerUser(UserDTO userDTO);
}
