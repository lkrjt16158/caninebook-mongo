package com.loki.caninebookmongo.web.error;

import com.loki.caninebookmongo.security.AuthenticationFailureException;
import com.loki.caninebookmongo.service.exceptions.PetAlreadyExistsException;
import com.loki.caninebookmongo.service.exceptions.UserAlreadyExistsException;
import com.loki.caninebookmongo.web.dto.ValidationFailedResponse;
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

    @Override
    protected ResponseEntity<Object> handleBindException( BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, generateErrorResponseForValidationFail(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        return handleExceptionInternal(ex, generateErrorResponseForValidationFail(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 400
    @ExceptionHandler({ FormMustContainPhoneOrEmailException.class })
    public ResponseEntity<Object> handlePhoneNumOrEmailMissing(final RuntimeException ex, final WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("phoneNumber",ex.getMessage());
        errors.put("email",ex.getMessage());
        ValidationFailedResponse validationFailedResponse = new ValidationFailedResponse();
        validationFailedResponse.setErrors(errors);
        return handleExceptionInternal(ex, validationFailedResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    //409
    @ExceptionHandler({ UserAlreadyExistsException.class })
    public ResponseEntity<Object> handleAlreadyExistsUserName(final UserAlreadyExistsException ex, final WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(),ex.getMessage());
        ValidationFailedResponse validationFailedResponse = new ValidationFailedResponse();
        validationFailedResponse.setErrors(errors);
        return handleExceptionInternal(ex, validationFailedResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    //409
    @ExceptionHandler({ PetAlreadyExistsException.class })
    public ResponseEntity<Object> handlePetAlreadyExistsException(final PetAlreadyExistsException ex, final WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("petName", ex.getPetName());
        errors.put("breed", ex.getBreed());
        errors.put("message", ex.getMessage());
        ValidationFailedResponse validationFailedResponse = new ValidationFailedResponse();
        validationFailedResponse.setErrors(errors);
        return handleExceptionInternal(ex, validationFailedResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    //403
    @ExceptionHandler({ AuthenticationFailureException.class })
    public ResponseEntity<Object> handleAlreadyExistsUserName(final AuthenticationFailureException ex, final WebRequest request) {
        Map<String, String > errors = new HashMap<>();
        errors.put("authentication", ex.getMessage());
        // return new ResponseEntity<>(authenticationResultResponseDTO,new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        return handleExceptionInternal(ex, new ValidationFailedResponse(errors), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }


    private static ValidationFailedResponse generateErrorResponseForValidationFail(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            System.out.println(errorMessage);
            errors.put(fieldName, errorMessage);

        });

        ValidationFailedResponse validationFailedResponse = new ValidationFailedResponse();
        validationFailedResponse.setErrors(errors);
        return validationFailedResponse;
    }


}
