package com.example.reporter.entities;

import java.math.BigDecimal;

public enum TransactionType {
    // Invoice type IDs defined by business rules
    DEBITO(1), BOLETO(2), FINANCIAMENTO(3),
    CREDITO(4), RECEBIMENTO_EMPRESTIMO(5), VENDAS(6), 
    RECEBIMENTO_TED(7), RECEBIMENTO_DOC(8), ALUGUEL(9);

    private int type;

    private TransactionType(int type) {
        this.type = type;
    }

    // Design pattern strategy

    public BigDecimal getSinal() {
        return switch (type) {
            // credit (Inflows of company resources) -> sum
            case 1, 4, 5, 6, 7, 8 -> new BigDecimal(1);
            // debit (Outflows of company resources), -> subtraction
            case 2, 3, 9 -> new BigDecimal(-1);
            default -> new BigDecimal(0);
        };
    }

    public static TransactionType findByType(int type) {
        for (TransactionType transactionType : values()) {
            if(transactionType.type == type) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Invalid tipo: " + type);
    }

}