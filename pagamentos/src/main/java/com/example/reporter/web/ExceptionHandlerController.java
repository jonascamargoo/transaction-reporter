package com.example.reporter.web;

import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(JobInstanceAlreadyCompleteException.class)
    private ResponseEntity<Object> handlerFileAlreadyImported(JobInstanceAlreadyCompleteException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("O arquivo informado já foi importado no sistema. Por padrão, o CNAB não é duplicado!");
    }
}
