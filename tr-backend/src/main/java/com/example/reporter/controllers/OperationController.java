package com.example.reporter.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporter.entities.OperationReport;
import com.example.reporter.services.OperationService;

@RestController
@RequestMapping("operacoes")     // curl http://localhost:8080/operacoes | json_pp
public class OperationController {
    private OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }
    
    @GetMapping
    @CrossOrigin(origins="http://localhost:9090")
    List<OperationReport> listAll() {
        return operationService.listTotalsOperationsByStoreName();
    }

    
}
