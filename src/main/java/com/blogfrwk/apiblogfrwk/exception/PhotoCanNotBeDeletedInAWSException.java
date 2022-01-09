package com.blogfrwk.apiblogfrwk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PhotoCanNotBeDeletedInAWSException extends Exception {
    public PhotoCanNotBeDeletedInAWSException() {
        super("There was a problem with the AWS s3 bucket");
    }
}
