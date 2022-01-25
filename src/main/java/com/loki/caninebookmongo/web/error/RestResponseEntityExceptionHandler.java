package com.loki.caninebookmongo.web.error;

import com.loki.caninebookmongo.service.exceptions.UserAlreadyExistsException;
import com.loki.caninebookmongo.web.dto.ValidationFailedResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    @Autowired
    private MessageSource messages;

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, generateErrorResponseForValidationFail(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        return handleExceptionInternal(ex, generateErrorResponseForValidationFail(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    private static ValidationFailedResponseDTO generateErrorResponseForValidationFail(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            System.out.println(errorMessage);
            errors.put(fieldName, errorMessage);
        });

        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO();
        validationFailedResponseDTO.setErrors(errors);
        return validationFailedResponseDTO;
    }

    // 400
    @ExceptionHandler({ FormMustContainPhoneOrEmailException.class })
    public ResponseEntity<Object> handlePhoneNumOrEmailMissing(final RuntimeException ex, final WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("phoneNumber",ex.getMessage());
        errors.put("email",ex.getMessage());
        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO();
        validationFailedResponseDTO.setErrors(errors);
        return handleExceptionInternal(ex, validationFailedResponseDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    public ResponseEntity<Object> handleAlreadyExistsUserName(final UserAlreadyExistsException ex, final WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(),ex.getMessage());
        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO();
        validationFailedResponseDTO.setErrors(errors);
        return handleExceptionInternal(ex, validationFailedResponseDTO, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


}
