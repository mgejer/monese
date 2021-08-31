package com.monese.banking.web;

import com.monese.banking.exceptions.BadRequestException;
import com.monese.banking.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity onRequestError(BadRequestException exception, WebRequest request) {
        logger.error("Exception found trying to process {}: {}", request, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity onNotFoundError(NotFoundException exception, WebRequest request) {
        logger.error("Exception found trying to process {}: {}", request, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity onError(Exception exception, WebRequest request) {
        logger.error("Exception found trying to process {}: {}", request, exception);
        return new ResponseEntity<>("Unexpected error found trying to process your request. Please trying again later", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
