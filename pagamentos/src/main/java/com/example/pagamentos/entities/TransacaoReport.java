package com.example.pagamentos.entities;

import java.math.BigDecimal;
import java.util.List;

public record TransacaoReport(
        String nomeDaLoja,
        BigDecimal total,
        List<Transacao> transacoes) {
        
        // Por ser um objeto imutavel, sempre crio um novo objeto a partir do original
        
        public TransacaoReport addTotal(BigDecimal valor) {
            return new TransacaoReport(nomeDaLoja, valor, transacoes);
        }

        public TransacaoReport addTransacao(Transacao transacao) {
            transacoes.add(transacao);
            return new TransacaoReport(nomeDaLoja, total, transacoes);
        }

}
