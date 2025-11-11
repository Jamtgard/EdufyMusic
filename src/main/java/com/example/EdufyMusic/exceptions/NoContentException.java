package com.example.EdufyMusic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// ED-80-SJ
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {

    private final String resource;

    public NoContentException(String resource) {
        super(String.format("%s is empty / null", resource));
        this.resource = resource;
    }

    public String getResource() {return resource;}
}
