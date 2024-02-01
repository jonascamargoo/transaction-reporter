package com.example.reporter.exceptions.customExceptions;


public class JAlreadyRunningException extends RuntimeException {
    public JAlreadyRunningException() {
        super("Já existe uma execução em andamento para o trabalho. Aguarde a conclusão da execução atual antes de iniciar uma nova.");
    }

    public JAlreadyRunningException(String message) {
        super(message);
    }

}