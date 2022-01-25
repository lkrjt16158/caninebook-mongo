package com.loki.caninebookmongo.service.impl;


import com.google.common.base.Strings;
import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.data.repository.UserRepository;
import com.loki.caninebookmongo.service.UserRegistrationService;
import com.loki.caninebookmongo.service.UserService;
import com.loki.caninebookmongo.service.exceptions.UserAlreadyExistsException;
import com.loki.caninebookmongo.web.dto.UserDTO;
import com.loki.caninebookmongo.web.error.FormMustContainPhoneOrEmailException;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {


    private final UserRepository userRepository;
    private final UserService userService;

    public UserRegistrationServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public User registerUser(UserDTO userDTO) {
        if(Strings.isNullOrEmpty(userDTO.getEmail()) && Strings.isNullOrEmpty(userDTO.getPhoneNumber())) {
            throw new FormMustContainPhoneOrEmailException("Phone or email required");
        }

        if(userService.userExistsByUserName(userDTO.getUserName())) {
            throw new UserAlreadyExistsException("user already exists with username: " + userDTO.getUserName(), "userName");
        }

        if(userService.userExistsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("user already exists with email: " + userDTO.getEmail(), "email");
        }

        if(userService.userExistsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new UserAlreadyExistsException("user already exists with phoneNumber: " + userDTO.getPhoneNumber(), "phoneNumber");
        }

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        user = userRepository.save(user);
        return user;
    }

}
