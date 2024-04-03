package com.VendorDetails.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class VendorExceptionHandler {
    @ExceptionHandler(value = {VendorNotFoundException.class})
    public ResponseEntity<Object> handleVendorNotFoundException
            (VendorNotFoundException vendorNotFoundException)
    {
        VendorException vendorException=new VendorException(
                vendorNotFoundException.getMessage(),
                vendorNotFoundException.getCause(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(vendorException,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map <String,String> error= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName=((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            error.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
