package com.nexoralabs.worksphere.shared.web;

import com.nexoralabs.worksphere.shared.api.ApiErrorResponse;
import com.nexoralabs.worksphere.shared.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;

public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        List<ApiErrorResponse.FieldViolation> violations = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiErrorResponse.FieldViolation(error.getField(), error.getDefaultMessage()))
                .toList();
        return errorResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed", request, violations);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request) {
        List<ApiErrorResponse.FieldViolation> violations = exception.getConstraintViolations()
                .stream()
                .map(error -> new ApiErrorResponse.FieldViolation(error.getPropertyPath().toString(), error.getMessage()))
                .toList();
        return errorResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed", request, violations);
    }

    @ExceptionHandler(ApiException.class)
    ResponseEntity<ApiErrorResponse> handleApiException(ApiException exception, HttpServletRequest request) {
        return errorResponse(exception.getStatus(), exception.getErrorCode(), exception.getMessage(), request, List.of());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception exception, HttpServletRequest request) {
        LOGGER.error("Unhandled API exception for path {}", request.getRequestURI(), exception);
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred", request, List.of());
    }

    private ResponseEntity<ApiErrorResponse> errorResponse(
            HttpStatus status,
            String errorCode,
            String message,
            HttpServletRequest request,
            List<ApiErrorResponse.FieldViolation> violations) {
        ApiErrorResponse response = new ApiErrorResponse(
                false,
                errorCode,
                message,
                Instant.now(),
                status.value(),
                request.getRequestURI(),
                violations);
        return ResponseEntity.status(status).body(response);
    }
}
