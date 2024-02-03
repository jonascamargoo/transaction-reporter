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

import com.example.reporter.entities.Operation;
import com.example.reporter.repository.OperationRepository;
import com.example.reporter.services.OperationService;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class) // Junit5
public class OperationServiceTest {
    // Test Pattern -> A - arrage; A - act; A - assert

    @InjectMocks // injetando o service e suas dependencias em cascata
    private OperationService operationService;

    @Mock
    private OperationRepository operationRepository;

    Faker faker = new Faker();

    @Test
    public void testlistTotalOperationsByStoreName() {
        final String lojaA = "Loja A", lojaB = "Loja B";

        var operation1 = new Operation(
                UUID.randomUUID(),
                1, new Date(System.currentTimeMillis()),
                BigDecimal.valueOf(100.00),
                faker.numerify("#########"),
                faker.numerify("####-####-####-####"),
                new Time(System.currentTimeMillis()),
                faker.name().fullName(),
                lojaA);

        var operation2 = new Operation(
                UUID.randomUUID(),
                2,
                new Date(System.currentTimeMillis()),
                BigDecimal.valueOf(50.00),
                faker.numerify("#########"),
                faker.numerify("####-####-####-####"),
                new Time(System.currentTimeMillis()),
                faker.name().fullName(),
                lojaB);

        var operation3 = new Operation(
                UUID.randomUUID(),
                1,
                new Date(System.currentTimeMillis()),
                BigDecimal.valueOf(75.00),
                faker.numerify("#########"),
                faker.numerify("####-####-####-####"),
                new Time(System.currentTimeMillis()),
                faker.name().fullName(),
                lojaA);

        // Since our operationService is a mock, the findBy... method won't return
        // actual objects from the database
        // Therefore, we'll use stubbing to set expectations

        var mockOperation = List.of(operation1, operation2, operation3);

        when(operationRepository.findAllByOrderByStoreNameAscIdDesc())
                .thenReturn(mockOperation);

        // The service logic is being used, but when it comes to its dependencies,
        // their behavior is mocked (I specify exactly what will happen).

        var reports = operationService.listTotalOperationsByStoreName();

        assertEquals(2, reports.size());

        reports.forEach(report -> {
            if (report.storeName().equals(lojaA)) {
                assertEquals(2, report.operations().size());
                assertEquals(BigDecimal.valueOf(175.00), report.total());
                assertTrue(report.operations().contains(operation1));
                assertTrue(report.operations().contains(operation3));
            } else if (report.storeName().equals(lojaB)) {
                assertEquals(1, report.operations().size());
                assertEquals(BigDecimal.valueOf(50.00), report.total());
                assertTrue(report.operations().contains(operation2));
            }
        });

    }
}
