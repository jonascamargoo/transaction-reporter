package com.example.reporter.exceptions.customExceptions;

public class JParametersInvalidException extends RuntimeException {
    public JParametersInvalidException() {
        super("Os parâmetros fornecidos para a execução do trabalho são inválidos. Certifique-se de que os parâmetros necessários estejam presentes e tenham valores válidos.");
    }

    public JParametersInvalidException(String message) {
        super(message);
    }
}
