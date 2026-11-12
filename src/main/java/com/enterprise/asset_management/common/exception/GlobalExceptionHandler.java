package com.enterprise.asset_management.common.exception;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://api.assetmanager.com/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ProblemDetail handleDuplicateResource(DuplicateResourceException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Resource Conflict");
        problemDetail.setType(URI.create("https://api.assetmanager.com/errors/conflict"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "One or more fields failed Validation");
        problemDetail.setTitle("Validation Failed");
        problemDetail.setType(URI.create("https://api.assetmanager.com/errors/bad-request"));
        problemDetail.setProperty("timestamp", Instant.now());

        Map<String, String> invalidFields = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            invalidFields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        problemDetail.setProperty("invalidFields", invalidFields);
        return problemDetail;
    }

    @ExceptionHandler(org.springframework.dao.OptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLocking(org.springframework.dao.OptimisticLockingFailureException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Resource was updated or checked out by another transaction. Please refresh and retry.");
        problemDetail.setTitle("Concurrent Modification Conflict");
        problemDetail.setType(URI.create("https://api.assetmanager.com/errors/concurrency-conflict"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalState(IllegalStateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage());
        problemDetail.setTitle("Invalid Business Operation");
        problemDetail.setType(URI.create("https://api.assetmanager.com/errors/invalid-state"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralExceptions(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected internal error occurred.");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.assetmanager.com/errors/internal-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
