package com.blogfrwk.apiblogfrwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhotoNotFoundException extends Exception {
    public PhotoNotFoundException(Long id) {
        super("Photo not found with ID " + id);
    }
}
