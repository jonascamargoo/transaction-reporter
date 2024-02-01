package com.example.reporter.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporter.entities.TransactionReport;
import com.example.reporter.services.TransactionService;

@RestController
@RequestMapping("transacoes")     // curl http://localhost:8080/transacoes | json_pp
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @GetMapping
    List<TransactionReport> listAll() {
        return transactionService.listTotalTransactionsByStoreName();
    }
}
