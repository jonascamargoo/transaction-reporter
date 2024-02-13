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
        // LinkedHashMap para preservar a ordem
        var reportMap = new LinkedHashMap<String, OperationReport>();
        
        operations.forEach(operation -> {
            String storeName = operation.opStoreName();
            BigDecimal value = operation.opValue();
            
            // Se a chave existir, eu a mantenho e adiciono o saldo. Se não existir, ela é inicializada com um saldo de 0
            
            reportMap.compute(storeName, (key, existingReport) -> {
                var report = (existingReport != null) ? existingReport 
                    : new OperationReport(key, BigDecimal.ZERO, new ArrayList<>());
                
                return report.addTotal(value).addOperation(operation);
            });
        });
        return new ArrayList<>(reportMap.values());

    }
        

}