package com.loki.caninebookmongo.security.auth.impl;

import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.security.auth.ApplicationUser;
import com.loki.caninebookmongo.security.auth.ApplicationUserDao;
import com.loki.caninebookmongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDaoService implements ApplicationUserDao {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ApplicationUser selectApplicationUser(String principal) {
        User user = userService.userIsValid(principal);
        return new ApplicationUser(
            user.getUserName(),
                user.getPassword(),
                null,
                true,
                true,
                true,
                true
        );
    }
}
