package com.loki.caninebookmongo.service.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    String field;

    public UserAlreadyExistsException(String message, String field ) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
