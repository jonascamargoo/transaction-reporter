package com.example.pagamentos.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pagamentos.entities.TransactionReport;
import com.example.pagamentos.services.TransactionService;

@RestController
@RequestMapping("transacoes")     // curl http://localhost:8080/transacoes | json_pp
public class TransacaoController {
    private TransactionService transactionService;

    public TransacaoController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @GetMapping
    List<TransactionReport> listAll() {
        return transactionService.listTotalTransactionsByStoreName();
    }
}
