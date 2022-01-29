package com.loki.caninebookmongo.web.controller;


import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.data.repository.UserRepository;
import com.loki.caninebookmongo.service.UserRegistrationService;
import com.loki.caninebookmongo.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class UserController {

    private final UserRegistrationService userRegistrationService;

    public UserController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<User> addUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userRegistrationService.registerUser(userDTO));
    }
}
