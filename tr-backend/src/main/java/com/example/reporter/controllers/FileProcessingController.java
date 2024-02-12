package com.example.reporter.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.reporter.services.FileProcessingService;


@RestController
@RequestMapping("api/upload")
public class FileProcessingController {

    private final FileProcessingService fileProcessingService;

    public FileProcessingController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    // curl -X POST -F "file=@CNAB.txt" http://localhost:8080/api/upload-file
    @PostMapping("file")
    @CrossOrigin(origins="http://localhost:4002")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        fileProcessingService.uploadFile(file);
        return "Processamento iniciado em background!";
    }

    @PostMapping("files")
    @CrossOrigin(origins="http://localhost:4002")
    public String uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        fileProcessingService.uploadFiles(files);
        return "Processamento iniciado em background para " + files.size() + " arquivo(s)!";
    }

}
