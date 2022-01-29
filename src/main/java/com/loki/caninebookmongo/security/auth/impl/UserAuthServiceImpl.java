package com.loki.caninebookmongo.security.auth.impl;

import com.loki.caninebookmongo.security.auth.ApplicationUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    @Autowired
    public UserAuthServiceImpl(ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return applicationUserDao.selectApplicationUser(s);
    }
}
