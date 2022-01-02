package com.blogfrwk.apiblogfrwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PhotoCanNotBeCreatedException extends Exception {
    public PhotoCanNotBeCreatedException() {
        super("Only the owner of the Post is authorized to create photos in the album");
    }
}
