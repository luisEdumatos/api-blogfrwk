package com.blogfrwk.apiblogfrwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PhotoCanNotBeDeletedException extends Exception {
    public PhotoCanNotBeDeletedException() {
        super("Only the owner of the Post is authorized to delete photos in the album");
    }
}
