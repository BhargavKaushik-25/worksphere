package com.nexoralabs.worksphere.shared.api;

import java.time.Instant;
import java.util.List;

/**
 * Standard API error envelope, including field-level validation details when available.
 */
public record ApiErrorResponse(
        boolean success,
        String errorCode,
        String message,
        Instant timestamp,
        int status,
        String path,
        List<FieldViolation> violations) {

    public record FieldViolation(String field, String message) {
    }
}
