package com.lambdaschool.dogsinitial.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DogNotFound extends RuntimeException {
    public DogNotFound(String message) {
        super(message);
    }
}