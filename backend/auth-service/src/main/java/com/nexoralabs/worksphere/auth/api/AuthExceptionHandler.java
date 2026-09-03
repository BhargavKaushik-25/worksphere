package com.nexoralabs.worksphere.auth.api;

import com.nexoralabs.worksphere.auth.service.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(AuthException.class)
    ResponseEntity<?> auth(AuthException exception) {
        return ResponseEntity.status(exception.getStatus()).body(Map.of("success", false,
                "errorCode", exception.getCode(), "message", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(Map.of("success", false, "errorCode", "VALIDATION_ERROR",
                "message", "Request validation failed"));
    }
}