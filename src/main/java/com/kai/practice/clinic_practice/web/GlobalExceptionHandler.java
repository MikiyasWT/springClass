package com.kai.practice.clinic_practice.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.kai.practice.clinic_practice.web.dto.ApiError;



@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex) {
       return ResponseEntity
                       .status(HttpStatus.BAD_REQUEST)
                       .body(new ApiError(ex.getMessage()));
    }
}