package com.blogfrwk.apiblogfrwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PostCanNotBeDeletedException extends Exception {
    public PostCanNotBeDeletedException() {
        super("Only the owner of the Post is authorized to delete it");
    }
}
