package com.example.reporter.exceptions.customExceptions;

public class FileTransferStateException extends RuntimeException {
    public FileTransferStateException() {
        super("Erro ao realizar a transferência do arquivo. O estado do objeto não permite a operação. Verifique se o arquivo é válido.");
    }

    public FileTransferStateException(String message) {
        super(message);
    }
}
