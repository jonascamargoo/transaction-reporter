package com.example.pagamentos.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pagamentos.entities.TransacaoReport;
import com.example.pagamentos.service.TransacaoService;

@RestController
@RequestMapping("transacoes")     // curl http://localhost:8080/transacoes | json_pp
public class TransacaoController {
    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }
    
    @GetMapping
    List<TransacaoReport> listAll() {
        return transacaoService.listTotaisTransacoesPorNomeDaLoja();
    }
}
