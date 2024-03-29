package com.example.reporter.entities;

import java.math.BigDecimal;

public enum OperationType {
	// IDs de tipo de fatura definidos por regras de negocios
	DEBITO(1), BOLETO(2), FINANCIAMENTO(3),
	CREDITO(4), RECEBIMENTO_EMPRESTIMO(5), VENDAS(6),
	RECEBIMENTO_TED(7), RECEBIMENTO_DOC(8), ALUGUEL(9);

	private int type;

	private OperationType(int type) {
		this.type = type;
	}

	// Design pattern: strategy

	public BigDecimal getSinal() {
		return switch (type) {
			// credito -> soma
			case 1, 4, 5, 6, 7, 8 -> new BigDecimal(1);
			// debito -> sub
			case 2, 3, 9 -> new BigDecimal(-1);
			default -> new BigDecimal(0);
		};
	}

	public static OperationType findByType(int type) {
		for (OperationType operationType : values()) {
			if (operationType.type == type) {
				return operationType;
			}
		}
		throw new IllegalArgumentException("Invalid tipo: " + type);
	}

}