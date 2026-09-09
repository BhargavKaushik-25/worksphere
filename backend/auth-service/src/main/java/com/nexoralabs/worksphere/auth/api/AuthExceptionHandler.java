package com.nexoralabs.worksphere.auth.api;

import com.nexoralabs.worksphere.auth.service.AuthException;
import com.nexoralabs.worksphere.shared.api.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(AuthException.class)
    ResponseEntity<ApiErrorResponse> auth(AuthException exception, HttpServletRequest request) {
        return error(exception.getStatus(), exception.getCode(), exception.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorResponse> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<ApiErrorResponse.FieldViolation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiErrorResponse.FieldViolation(error.getField(), error.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ApiErrorResponse(false, "VALIDATION_ERROR",
                "Request validation failed", Instant.now(), 400, request.getRequestURI(), violations));
    }

    private ResponseEntity<ApiErrorResponse> error(int status, String code, String message, HttpServletRequest request) {
        ApiErrorResponse response = new ApiErrorResponse(false, code, message, Instant.now(), status,
                request.getRequestURI(), List.of());
        return ResponseEntity.status(status).body(response);
    }
}