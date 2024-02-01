package com.example.reporter.exceptions.customExceptions;

public class JRestartException extends RuntimeException {
    public JRestartException() {
        super("Erro ao tentar reiniciar o trabalho. Certifique-se de que as condições necessárias para reiniciar estão atendidas e verifique se não há problemas internos impedindo a reinicialização.");
    }

    public JRestartException(String message) {
        super(message);
    }
}
