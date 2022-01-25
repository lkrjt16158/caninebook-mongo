package com.loki.caninebookmongo.web.error;

public class FormMustContainPhoneOrEmailException extends RuntimeException {
    public FormMustContainPhoneOrEmailException(String phone_or_email_required) {
        super(phone_or_email_required);
    }

    public FormMustContainPhoneOrEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
