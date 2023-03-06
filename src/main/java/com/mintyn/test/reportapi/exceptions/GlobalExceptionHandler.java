package com.mintyn.test.reportapi.exceptions;

import com.mintyn.test.reportapi.dtos.StandardResponse;
import com.mintyn.test.reportapi.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex , new StandardResponse(Status.NOT_FOUND,  ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex , new StandardResponse(Status.FAILED_VALIDATION,  ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(value = { Exception.class, RuntimeException.class})
    protected ResponseEntity<Object> handleUnknownException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new StandardResponse(Status.INTERNAL_ERROR,  ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();


        Optional<StandardResponse> error = bindingResult.getFieldErrors().stream().findFirst().map(m -> new StandardResponse(Status.FAILED_VALIDATION, m.getDefaultMessage()));

        if (error.isEmpty()) {
            return new ResponseEntity<>(new StandardResponse(Status.FAILED_VALIDATION, "invalid parameter"), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}