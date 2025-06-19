package com.example.demo.Exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildErrorBody(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return body;
    }

    // Lỗi validate @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(fieldErrors);
    }

    // Lỗi validate @Validated (thường dùng cho @RequestParam, @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(buildErrorBody(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Không tìm thấy tài nguyên
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorBody(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    // Lỗi phân quyền
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildErrorBody("Access Denied", HttpStatus.FORBIDDEN));
    }

    // Phương thức không hỗ trợ
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(buildErrorBody(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED));
    }

    // Lỗi runtime khác
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorBody(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Lỗi chung chung không xác định
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorBody("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
        String message = "Invalid value: " + ex.getValue();

        // Nếu lỗi liên quan đến Enum
        if (ex.getTargetType().isEnum()) {
            Object[] enumConstants = ex.getTargetType().getEnumConstants();
            String expectedValues = java.util.Arrays.toString(enumConstants);
            message = String.format(
                    "Invalid enum value '%s'. Allowed values are: %s",
                    ex.getValue(), expectedValues
            );
        }

        return ResponseEntity.badRequest().body(buildErrorBody(message, HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEnumParseError(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        // Nếu lỗi là do enum sai
        if (cause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            String enumName = ife.getTargetType().getSimpleName();
            String invalidValue = ife.getValue().toString();
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Invalid value '" + invalidValue + "' for enum " + enumName)
            );
        }

        // Các lỗi khác
        return ResponseEntity.badRequest().body(
                Map.of("error", "Invalid request payload")
        );
    }
    @ExceptionHandler(EmployeeAlreadyExists.class)
    public ResponseEntity<?> handleEmployeeAlreadyExists(EmployeeAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildErrorBody(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(InsufficientLeaveBalanceException.class)
    public ResponseEntity<?> handleInsufficientLeaveBalance(InsufficientLeaveBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorBody(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<?> handleInvalidDateRange(InvalidDateRangeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorBody(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(LeaveRequestConflictException.class)
    public ResponseEntity<?> handleLeaveRequestConflict(LeaveRequestConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildErrorBody(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<?> handleUnauthorizedOperation(UnauthorizedOperationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildErrorBody(ex.getMessage(), HttpStatus.FORBIDDEN));
    }
}
