package com.example.pagamentos.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.pagamentos.entities.TransacaoReport;
import com.example.pagamentos.repository.TransacaoRepository;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;
    
    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    
    public List<TransacaoReport> listTotaisTransacoesPorNomeDaLoja() {
        var transacoes = repository.findAllByOrderByNomeDaLojaAscIdDesc();
        // Um linkedHashmap, pois queremos preservar a ordem, e apenas o HashMap nao preserva a ordem
        var reportMap = new LinkedHashMap<String, TransacaoReport>();

        transacoes.forEach(transacao -> {
            var nomeDaLoja = transacao.nomeDaLoja();
            BigDecimal valor = transacao.valor();


            // se na chave existe algo, eu mantenho ela e adiciono o saldo. Se nao, ela eh inicializada com saldo 0;
            reportMap.compute(nomeDaLoja, (key, existingReport) -> {
                var report = (existingReport != null) ? existingReport : new TransacaoReport(key, BigDecimal.ZERO, new ArrayList<>());

                return report.addTotal(valor).addTransacao(transacao);
            });
        });

        return new ArrayList<>(reportMap.values());

    }

}