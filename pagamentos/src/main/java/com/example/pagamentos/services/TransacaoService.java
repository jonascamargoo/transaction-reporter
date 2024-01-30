package com.example.pagamentos.services;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.pagamentos.entities.TransactionReport;
import com.example.pagamentos.repository.TransactionRepository;

@Service
public class TransacaoService {

    private final TransactionRepository transactionRepository;
    
    public TransacaoService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    public List<TransactionReport> listsTotalTransactionsByStoreName() {
        var transactions = transactionRepository.findAllByOrderByStoreNameAscIdDesc();
        // A LinkedHashMap, as we want to preserve the order, and a regular HashMap does not preserve order
        var reportMap = new LinkedHashMap<String, TransactionReport>();
        transactions.forEach(transaction -> {
            String storeName = transaction.storeName();
            BigDecimal value = transaction.value();
            // If the key exists, I keep it and add the balance. If not, it is initialized with a balance of 0
            reportMap.compute(storeName, (key, existingReport) -> {
                var report = (existingReport != null) ? existingReport : new TransactionReport(key, BigDecimal.ZERO, new ArrayList<>());
                return report.addTotal(value).addTransaction(transaction);
            });
        });
        

        return new ArrayList<>(reportMap.values());

    }

}