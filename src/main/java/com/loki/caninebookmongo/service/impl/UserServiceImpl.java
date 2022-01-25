package com.loki.caninebookmongo.service.impl;

import com.loki.caninebookmongo.data.repository.UserRepository;
import com.loki.caninebookmongo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean userExistsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean userExistsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
