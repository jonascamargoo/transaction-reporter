package com.example.reporter.exceptions.customExceptions;

public class FileTransferIOException extends RuntimeException {
    public FileTransferIOException() {
        super("Erro de I/O durante a transferência do arquivo. Verifique se o arquivo é válido e se o local de destino está acessível.");
    }

    public FileTransferIOException(String message) {
        super(message);
    }
}
