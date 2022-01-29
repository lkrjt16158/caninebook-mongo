package com.loki.caninebookmongo.security.auth;

public interface ApplicationUserDao {

     ApplicationUser selectApplicationUser(String principal);
}
