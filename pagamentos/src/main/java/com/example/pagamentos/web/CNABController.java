package com.example.pagamentos.web;

import java.io.IOException;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.pagamentos.service.CNABService;


@RestController
@RequestMapping("cnab")
public class CNABController {

    private final CNABService cnabService;

    public CNABController(CNABService cnabService) {
        this.cnabService = cnabService;
    }

    // testei com curl -X POST -F "file=@CNAB.txt" http://localhost:8080/cnab/upload
    
    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        cnabService.uploadCNABFile(file);
        return "Processamento iniciado em background!";
    }

}
