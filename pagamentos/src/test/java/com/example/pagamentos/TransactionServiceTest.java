package com.example.pagamentos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.reporter.entities.Transaction;
import com.example.reporter.repository.TransactionRepository;
import com.example.reporter.services.TransactionService;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class) // Junit5
public class TransactionServiceTest {

    @InjectMocks // injetando o service e suas dependencias em cascata
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    Faker faker = new Faker();

    @Test
    public void testlistTotaisTransacoesByNomeDaLoja() {
        final String lojaA = "Loja A", lojaB = "Loja B";

        var transaction1 = new Transaction(
                UUID.randomUUID(),
                1, new Date(System.currentTimeMillis()),
                BigDecimal.valueOf(100.00),
                faker.numerify("#########"),
                faker.numerify("####-####-####-####"),
                new Time(System.currentTimeMillis()),
                faker.name().fullName(),
                lojaA);

        var transaction2 = new Transaction(
                UUID.randomUUID(),
                2,
                new Date(System.currentTimeMillis()),
                BigDecimal.valueOf(50.00),
                faker.numerify("#########"),
                faker.numerify("####-####-####-####"),
                new Time(System.currentTimeMillis()),
                faker.name().fullName(),
                lojaB);

        var transaction3 = new Transaction(
                UUID.randomUUID(),
                1,
                new Date(System.currentTimeMillis()),
                BigDecimal.valueOf(75.00),
                faker.numerify("#########"),
                faker.numerify("####-####-####-####"),
                new Time(System.currentTimeMillis()),
                faker.name().fullName(),
                lojaA);

        // Since our transactionService is a mock, the findBy... method won't return
        // actual objects from the database
        // Therefore, we'll use stubbing to set expectations

        var mockTransaction = List.of(transaction1, transaction2, transaction3);

        when(transactionRepository.findAllByOrderByStoreNameAscIdDesc())
                .thenReturn(mockTransaction);

        // The service logic is being used, but when it comes to its dependencies,
        // their behavior is mocked (I specify exactly what will happen).

        var reports = transactionService.listTotalTransactionsByStoreName();

        assertEquals(2, reports.size());

        reports.forEach(report -> {
            if (report.storeName().equals(lojaA)) {
                assertEquals(2, report.transactions().size());
                assertEquals(BigDecimal.valueOf(175.00), report.total());
                assertTrue(report.transactions().contains(transaction1));
                assertTrue(report.transactions().contains(transaction3));
            } else if (report.storeName().equals(lojaB)) {
                assertEquals(1, report.transactions().size());
                assertEquals(BigDecimal.valueOf(50.00), report.total());
                assertTrue(report.transactions().contains(transaction2));
            }
        });

    }
}
