package com.example.reporter.exceptions.customExceptions;

public class JInstanceAlreadyCompleteException extends RuntimeException {
    public JInstanceAlreadyCompleteException() {
        super("O arquivo informado já foi importado no sistema. Por padrão, o CNAB não é duplicado.");
    }
    public JInstanceAlreadyCompleteException(String message) {
        super(message);
    }
}
