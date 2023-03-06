package com.mintyn.test.reportapi.controllers;

import com.mintyn.test.reportapi.dtos.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class BaseController {

    public ResponseEntity<StandardResponse> updateResponseStatus(StandardResponse response) {
        try {
            switch (response.getStatus()) {
                case SUCCESS:
                case PROCESSING:
                    return new ResponseEntity<>(response, HttpStatus.OK);
                case CREATED:
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                case NOT_FOUND:
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                case NO_CONTENT:
                    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
                case INTERNAL_ERROR:
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            log.error("An unknown exception occurred : {}", BaseController.class, e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
