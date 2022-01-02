package com.blogfrwk.apiblogfrwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CommentCanNotBeUpdatedException extends Exception {
    public CommentCanNotBeUpdatedException() {
        super("Only the owner of the Comment is authorized to update it");
    }
}
