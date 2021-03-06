package com.loki.caninebookmongo.security;

public class AuthenticationFailureException extends RuntimeException{

    public AuthenticationFailureException(String message) {
        super(message);
    }

    public AuthenticationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
