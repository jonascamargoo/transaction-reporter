package com.example.reporter.entities;

import java.math.BigDecimal;
import java.util.List;

public record OperationReport(
        String storeName,
        BigDecimal total,
        List<Operation> operations) {
        
        // Wither Pattern
        
        public OperationReport addTotal(BigDecimal value) {
            return new OperationReport(storeName, total.add(value), operations);
        }

        public OperationReport addOperation(Operation operation) {
            operations.add(operation);
            return new OperationReport(storeName, total, operations);
        }

}
