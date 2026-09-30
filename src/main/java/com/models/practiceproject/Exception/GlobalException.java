package com.models.practiceproject.Exception;


import com.models.practiceproject.payload.ApiResponse;
import com.models.practiceproject.payload.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleStudentNotFoundException(
            StudentNotFoundException ex, HttpServletRequest servletRequest) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(
                    false,
                    LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.value(),
//                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    ex.getMessage(),
                    null

            );
            return  new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmailException(
            DuplicateEmailException ex, HttpServletRequest servletRequest) {
        ApiResponse<Object> apiResponse = new ApiResponse<> (
                false,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
//                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                null
//                servletRequest.getRequestURI()
        );
        return  new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest servletRequest) {
        Map<String,Object> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach((fieldError) -> {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                });
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                errors

//                servletRequest.getRequestURI()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
