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

    // Applying the Wither Pattern. Used this.*() to indicate what is being changed or not, as exemplified in the commented method

    // public Operation withValor(BigDecimal valor) {
    //     return new Operation(
    //         this.id(), this.type(), this.date(),
    //         value, this.cpf(), this.card(),
    //         this.hour(), this.storeOwner(), this.storeName());
    // }


    public Operation withValor(BigDecimal valor) {
        return new Operation(
            id, type, date,
            value, cpf, card,
            hour, storeName, storeName);
    }

    // public Operation withData(String data) throws ParseException {
    //     var dateFormat = new SimpleDateFormat("yyyyMMdd");
    //     var date = dateFormat.parse(data);

    //     return new Operation(
    //         this.id(), this.type(), new Date(date.getTime()),
    //         this.value(), this.cpf(), this.card(),
    //         this.hour(), this.storeOwner(), this.storeName());
    // }

    
    public Operation withDate(String date) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyyMMdd");
        var parsedDate = dateFormat.parse(date);
        return new Operation(
            id, type, new Date(parsedDate.getTime()), 
            value, cpf, card, 
            hour, storeOwner, storeName);
    }

    // public Operation withHora(String hour) throws ParseException {
    //     var dateFormat = new SimpleDateFormat("HHmmss");
    //     var parsedHour = dateFormat.parse(hour);

    //     return new Operation(
    //         this.id(), this.type(), this.date(),
    //         this.value(), this.cpf(), this.card(),
    //         new Time(hour.getTime()), this.storeOwner(), this.storeName());
    // }

    public Operation withHour(String hour) throws ParseException {
        var dateFormat = new SimpleDateFormat("HHmmss");
        var parsedHour = dateFormat.parse(hour);

        return new Operation(
            id, type, date,
            value, cpf, card,
            new Time(parsedHour.getTime()), storeOwner, storeName);
    }

}
