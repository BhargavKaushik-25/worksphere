package com.nexoralabs.worksphere.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception for expected API failures with a stable client-facing error code.
 */
public class ApiException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

    public ApiException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
