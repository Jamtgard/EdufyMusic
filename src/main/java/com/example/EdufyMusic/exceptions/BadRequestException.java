package com.example.EdufyMusic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// ED-235-SJ
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private final String resource;
    private final String fieldName;
    private final String fieldValue;

    public BadRequestException(String resource, String fieldName, String fieldValue) {
        super(String.format("%s - Value is not allowed for %s : %s", resource, fieldName, fieldValue));
        this.resource = resource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResource() {return resource;}
    public String getFieldName() {return fieldName;}
    public String getFieldValue() {return fieldValue;}
}
