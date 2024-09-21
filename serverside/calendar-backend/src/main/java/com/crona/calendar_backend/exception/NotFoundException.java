package com.crona.calendar_backend.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String message) {
        super(HttpStatusCode.valueOf(404), message);
    }
}
