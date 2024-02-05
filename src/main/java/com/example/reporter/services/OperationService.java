package com.example.reporter.services;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.reporter.entities.OperationReport;
import com.example.reporter.repository.OperationRepository;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }
    
    public List<OperationReport> listTotalsOperationsByStoreName() {
        var operations = operationRepository.findAllByOrderByOpStoreNameAscOpIdDesc();
        // A LinkedHashMap, as we want to preserve the order, and a regular HashMap doesnt
        var reportMap = new LinkedHashMap<String, OperationReport>();
        
        operations.forEach(operation -> {
            String storeName = operation.opStoreName();
            BigDecimal value = operation.opValue();
            // If the key exists, I keep it and add the balance. If not, it is initialized with a balance of 0
            reportMap.compute(storeName, (key, existingReport) -> {
                var report = (existingReport != null) ? existingReport 
                    : new OperationReport(key, BigDecimal.ZERO, new ArrayList<>());
                
                return report.addTotal(value).addOperation(operation);
            });
        });
        return new ArrayList<>(reportMap.values());

    }        
        

}