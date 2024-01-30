package com.example.pagamentos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.pagamentos.entities.Transacao;
import com.example.pagamentos.repository.TransacaoRepository;
import com.example.pagamentos.services.TransacaoService;

@ExtendWith(MockitoExtension.class) //Junit5
public class TransacaoServiceTest {
    
    @InjectMocks // injetando o service e suas dependencias em cascata
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    public void testlistTotaisTransacoesByNomeDaLoja() {
        final String lojaA = "Loja A", lojaB = "Loja B";

        var transacao1 = new Transacao(
            1L, 1, new Date(System.currentTimeMillis()), BigDecimal.valueOf(100.00), 123456789L, "1234-5678-9012-345", new Time(System.currentTimeMillis()), "Dono da Loja A", lojaA);

        var transacao2 = new Transacao(
            2L, 1, new Date(System.currentTimeMillis()), BigDecimal.valueOf(50.00),
            987654321L, "0987-6543-2198-7654", new Time(System.currentTimeMillis()), "Dono da Loja B", lojaB);
        
        var transacao3 = new Transacao(
            3L, 1, new Date(System.currentTimeMillis()), BigDecimal.valueOf(75.00),
            555555555L, "1346-7946-1346-7946", new Time(System.currentTimeMillis()), "Dono da Loja A", lojaA);

        
        // Como nosso transacaoRepository eh um mock, o metodo findBy... nao retornara objetos reais do banco. Portanto, usaremos stub - estabelecer expectativas
        var mockTransacoes = List.of(transacao1, transacao2, transacao3);

        when(transacaoRepository.findAllByOrderByNomeDaLojaAscIdDesc())
            .thenReturn(mockTransacoes);

        // a logica do service ta sendo utilizada, mas quando chega em suas dependencias, seu comportamento eh mockado (digo exatamente o que ira acontecer)

        var reports = transacaoService.listTotaisTransacoesPorNomeDaLoja();

        assertEquals(2, reports.size());

        reports.forEach(report -> {
            if(report.nomeDaLoja().equals(lojaA)) {
                assertEquals(2, report.transacoes().size());
                assertEquals(BigDecimal.valueOf(175.00), report.total());
                assertTrue(report.transacoes().contains(transacao1));
                assertTrue(report.transacoes().contains(transacao3));
            } else if(report.nomeDaLoja().equals(lojaB)) {
                assertEquals(1, report.transacoes().size());
                assertEquals(BigDecimal.valueOf(50.00), report.total());
                assertTrue(report.transacoes().contains(transacao2));
                
            }
        });

    }
}
