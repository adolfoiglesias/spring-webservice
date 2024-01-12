package com.amichdev.spring.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class DomainNotFoundException extends  RuntimeException {

    public DomainNotFoundException() {
    }

    public DomainNotFoundException(String message) {
        super(message);
    }
}
