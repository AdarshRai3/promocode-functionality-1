package com.bootcodingTask.promocode_functionality.promocode_functionality.advice;

import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse.ErrorsResponse;
import com.bootcodingTask.promocode_functionality.promocode_functionality.exceptions.DuplicatePromoCodeException;
import com.bootcodingTask.promocode_functionality.promocode_functionality.exceptions.InvalidPromoCodeException;
import com.bootcodingTask.promocode_functionality.promocode_functionality.exceptions.ResourceNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Data
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ErrorsResponse errorResponse = new ErrorsResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errorMap,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorsResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());

        ErrorsResponse errorResponse = new ErrorsResponse(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                errorMap,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatePromoCodeException.class)
    public ResponseEntity<ErrorsResponse> handleDuplicatePromoCodeException(DuplicatePromoCodeException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("code", ex.getMessage());

        ErrorsResponse errorResponse = new ErrorsResponse(
                HttpStatus.CONFLICT.value(),
                "Duplicate PromoCode",
                errorMap,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPromoCodeException.class)
    public ResponseEntity<ErrorsResponse> handleInvalidPromoCodeException(InvalidPromoCodeException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("promocode", ex.getMessage());

        ErrorsResponse errorResponse = new ErrorsResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid PromoCode",
                errorMap,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Your existing global exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorsResponse> handleGlobalException(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());

        ErrorsResponse errorResponse = new ErrorsResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred",
                errorMap,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}