package com.example.reporter.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.reporter.exceptions.customExceptions.FileTransferIOException;
import com.example.reporter.exceptions.customExceptions.FileTransferStateException;
import com.example.reporter.exceptions.customExceptions.JAlreadyRunningException;
import com.example.reporter.exceptions.customExceptions.JInstanceAlreadyCompleteException;
import com.example.reporter.exceptions.customExceptions.JParametersInvalidException;
import com.example.reporter.exceptions.customExceptions.JRestartException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(FileTransferStateException.class)
    private ResponseEntity<RestErrorMessage> fileTransferStateHandler(FileTransferStateException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

    @ExceptionHandler(FileTransferIOException.class)
    private ResponseEntity<RestErrorMessage> fileTransforIOHandler(FileTransferIOException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

    @ExceptionHandler(JAlreadyRunningException.class)
    private ResponseEntity<RestErrorMessage> jAlreadyRunningHandler(JAlreadyRunningException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(JRestartException.class)
    private ResponseEntity<RestErrorMessage> jRestartHandler(JRestartException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

    @ExceptionHandler(JInstanceAlreadyCompleteException.class)
    private ResponseEntity<RestErrorMessage> jInstanceAlreadyCompleteHandler(JInstanceAlreadyCompleteException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(JParametersInvalidException.class)
    private ResponseEntity<RestErrorMessage> jParametersInvalidHandler(JParametersInvalidException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

  


   
}
