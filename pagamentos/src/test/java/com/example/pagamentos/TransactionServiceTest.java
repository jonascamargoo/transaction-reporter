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

import com.example.pagamentos.entities.Transaction;
import com.example.pagamentos.repository.TransactionRepository;
import com.example.pagamentos.services.TransactionService;

@ExtendWith(MockitoExtension.class) //Junit5
public class TransactionServiceTest {
    
    @InjectMocks // injetando o service e suas dependencias em cascata
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testlistTotaisTransacoesByNomeDaLoja() {
        final String lojaA = "Loja A", lojaB = "Loja B";

        var transaction1 = new Transaction(
            UUID.randomUUID(), 1, new Date(System.currentTimeMillis()), BigDecimal.valueOf(100.00), "123456789", "1234-5678-9012-345", new Time(System.currentTimeMillis()), "Dono da Loja A", lojaA);

        var transaction2 = new Transaction(
            UUID.randomUUID(), 1, new Date(System.currentTimeMillis()), BigDecimal.valueOf(50.00),
            "987654321", "0987-6543-2198-7654", new Time(System.currentTimeMillis()), "Dono da Loja B", lojaB);
        
        var transaction3 = new Transaction(
            UUID.randomUUID(), 1, new Date(System.currentTimeMillis()), BigDecimal.valueOf(75.00),
            "555555555", "1346-7946-1346-7946", new Time(System.currentTimeMillis()), "Dono da Loja A", lojaA);

        
        // Como nosso transactionService eh um mock, o metodo findBy... nao retornara objetos reais do banco. Portanto, usaremos stub - estabelecer expectativas
        var mockTransaction = List.of(transaction1, transaction2, transaction3);

        when(transactionRepository.findAllByOrderByStoreNameAscIdDesc())
            .thenReturn(mockTransaction);

        // a logica do service ta sendo utilizada, mas quando chega em suas dependencias, seu comportamento eh mockado (digo exatamente o que ira acontecer)

        var reports = transactionService.listTotalTransactionsByStoreName();

        assertEquals(2, reports.size());

        reports.forEach(report -> {
            if(report.storeName().equals(lojaA)) {
                assertEquals(2, report.transactions().size());
                assertEquals(BigDecimal.valueOf(175.00), report.total());
                assertTrue(report.transactions().contains(transaction1));
                assertTrue(report.transactions().contains(transaction3));
            } 
            else if(report.storeName().equals(lojaB)) {
                assertEquals(1, report.transactions().size());
                assertEquals(BigDecimal.valueOf(50.00), report.total());
                assertTrue(report.transactions().contains(transaction2));
            }
        });

    }
}
