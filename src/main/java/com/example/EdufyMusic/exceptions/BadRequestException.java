package com.example.EdufyMusic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// ED-235-SJ
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private String resource;
    private String fieldName;
    private Object fieldValue;
    private String message;

    public BadRequestException(String resource, String fieldName, Object fieldValue) {
        super(String.format("%s - Value is not allowed for %s : %s", resource, fieldName, fieldValue));
        this.resource = resource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public String getResource() {return resource;}
    public String getFieldName() {return fieldName;}
    public Object getFieldValue() {return fieldValue;}
    public String getMessage() {return message;}
}
