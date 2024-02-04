package com.example.reporter.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record Operation(
    @Id UUID id,
    Integer type,
    Date date,
    BigDecimal value,
    String cpf,
    String card,
    Time hour,
    @Column("store_owner") String storeOwner,
    @Column("store_name") String storeName
) {

    // Applying the Wither Pattern
    
    public Operation withValor(BigDecimal valor) {
        return new Operation(
            id, type, date,
            value, cpf, card,
            hour, storeName, storeName);
    }

    public Operation withDate(String date) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyyMMdd");
        var parsedDate = dateFormat.parse(date);
        return new Operation(
            id, type, new Date(parsedDate.getTime()), 
            value, cpf, card, 
            hour, storeOwner, storeName);
    }

    public Operation withHour(String hour) throws ParseException {
        var dateFormat = new SimpleDateFormat("HHmmss");
        var parsedHour = dateFormat.parse(hour);

        return new Operation(
            id, type, date,
            value, cpf, card,
            new Time(parsedHour.getTime()), storeOwner, storeName);
    }

}
