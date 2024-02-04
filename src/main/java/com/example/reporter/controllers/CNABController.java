package com.example.reporter.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.reporter.services.CNABFileProcessingService;


@RestController
@RequestMapping("cnab")
public class CNABController {

    private final CNABFileProcessingService cnabFileProcessingService;

    public CNABController(CNABFileProcessingService cnabFileProcessingService) {
        this.cnabFileProcessingService = cnabFileProcessingService;
    }

    // testei com curl -X POST -F "file=@CNAB.txt" http://localhost:8080/cnab/execute
    
    @PostMapping("execute")
    public String upload(@RequestParam("file") MultipartFile file) {
        cnabFileProcessingService.executeJob(file);
        return "Processamento iniciado em background!";
    }

}
