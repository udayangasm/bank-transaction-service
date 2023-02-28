package com.whitebox.bank.exception;

import org.springframework.http.HttpStatus;

public class MandatoryFieldNotFoundException extends RuntimeException {

    public MandatoryFieldNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}