package com.example.reporter.exceptions.customExceptions;

public class JInstanceAlreadyCompleteException extends RuntimeException {
    public JInstanceAlreadyCompleteException() {
        super("Não é possível reiniciar o trabalho, pois a instância do trabalho já foi concluída anteriormente com êxito.");
    }
    public JInstanceAlreadyCompleteException(String message) {
        super(message);
    }
}
