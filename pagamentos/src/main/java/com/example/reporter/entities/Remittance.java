package com.example.reporter.entities;

import java.math.BigDecimal;

public record Remittance(
    Integer type,
    String date,
    BigDecimal value,
    String cpf,
    String card,
    String hour,
    String storeOwner,
    String storeName) {
    
}
