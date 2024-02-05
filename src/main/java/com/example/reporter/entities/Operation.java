package com.example.reporter.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record Operation(
    // @Column to distinguish column names from reserved names
    @Id Long opId,
    @Column("op_type") Integer opType,
    @Column("op_date") Date opDate,
    @Column("op_value") BigDecimal opValue,
    @Column("op_cpf") String opCpf,
    @Column("op_card") String opCard,
    @Column("op_hour") Time opHour,
    @Column("op_store_owner") String opStoreOwner,
    @Column("op_store_name") String opStoreName
) {
    // Applying the Wither Pattern
    
    public Operation withValor(BigDecimal valor) {
        return new Operation(
            opId, opType, opDate,
            opValue, opCpf, opCard,
            opHour, opStoreOwner, opStoreName);
    }

    public Operation withDate(String date) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyyMMdd");
        var parsedDate = dateFormat.parse(date);
        return new Operation(
            opId, opType, new Date(parsedDate.getTime()), 
            opValue, opCpf, opCard, 
            opHour, opStoreOwner, opStoreName);
    }

    public Operation withHour(String hour) throws ParseException {
        var dateFormat = new SimpleDateFormat("HHmmss");
        var parsedHour = dateFormat.parse(hour);
        return new Operation(
            opId, opType, opDate,
            opValue, opCpf, opCard,
            new Time(parsedHour.getTime()), opStoreOwner, opStoreName);
    }

}
