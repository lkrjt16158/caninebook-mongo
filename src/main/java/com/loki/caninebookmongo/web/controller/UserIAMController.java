package com.loki.caninebookmongo.web.controller;

import com.loki.caninebookmongo.security.AuthenticationFailureException;
import com.loki.caninebookmongo.security.auth.Token;
import com.loki.caninebookmongo.security.config.SecurityConfig;
import com.loki.caninebookmongo.service.exceptions.UserInvalidException;
import com.loki.caninebookmongo.web.dto.UserAuthenticationSuccessfulResponse;
import com.loki.caninebookmongo.web.dto.UserNameAndPasswordAuthenticationRequestDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;

@RequestMapping("/api/iam")
@RestController
public class UserIAMController {

    private final SecurityConfig securityConfig;

    public UserIAMController(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationSuccessfulResponse> UserLogin(@RequestBody @Valid UserNameAndPasswordAuthenticationRequestDTO userNameAndPasswordAuthenticationRequest) {
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(
                userNameAndPasswordAuthenticationRequest.getUsername(),
                userNameAndPasswordAuthenticationRequest.getPassword()
        );
        boolean authFailure = true;
        Authentication authentication = null;
        try {
            authentication = securityConfig.authenticationManagerBean()
                    .authenticate(authenticationRequest);
            if(authentication.isAuthenticated())
                authFailure = false;

        } catch (AuthenticationException e) {

            if(e instanceof BadCredentialsException) {
                throw new AuthenticationFailureException("Incorrect password!");
            }

            if(e.getCause() instanceof UserInvalidException) {
                throw new AuthenticationFailureException("No such user exists!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(authFailure) {
            throw new AuthenticationFailureException("User authentication failure");
        }


        String token = Jwts.builder()
                .setSubject(authentication.getName())
                //.claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(Token.SECURITY_KEY.getBytes()))
                .compact();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Bearer " + token);
        UserAuthenticationSuccessfulResponse userAuthenticationSuccessfulResponse = new UserAuthenticationSuccessfulResponse();
        return new ResponseEntity<>(userAuthenticationSuccessfulResponse,httpHeaders, HttpStatus.OK);



    }
}
