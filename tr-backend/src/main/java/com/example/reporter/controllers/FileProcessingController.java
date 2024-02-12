package com.example.reporter.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.reporter.services.FileProcessingService;


@RestController
@RequestMapping("api")
public class FileProcessingController {

    private final FileProcessingService fileProcessingService;

    public FileProcessingController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    // curl -X POST -F "file=@CNAB.txt" http://localhost:8080/api/upload
    @PostMapping("upload")
    @CrossOrigin(origins="http://localhost:4200")
    public String upload(@RequestParam("file") MultipartFile file) {
        fileProcessingService.upload(file);
        return "Processamento iniciado em background!";
    }

}
