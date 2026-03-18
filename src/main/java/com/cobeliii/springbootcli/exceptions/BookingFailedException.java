package com.cobeliii.springbootcli.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookingFailedException extends RuntimeException{
    public BookingFailedException(String message) {
        super(message);
    }
}
