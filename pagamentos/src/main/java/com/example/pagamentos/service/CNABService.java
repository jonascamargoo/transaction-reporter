package com.example.pagamentos.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CNABService {
    private final Path fileStorageLocation;

    // como em config o job esta anotado como Bean, o spring, ao inicializar, roda o job automaticamente. Logo, irei desativar esse comportamento em applications.properties. Depois, irei sinalizar com @Qualifier("jobLauncherAsync")

    //async
    private final JobLauncher jobLauncher;

    //sync
    private final Job job;

    public CNABService(@Value("${file.upload-dir}")String fileUploadDir, @Qualifier("jobLauncherAsync") JobLauncher jobLauncher, Job job) {
        this.fileStorageLocation = Paths.get(fileUploadDir);
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public void uploadCNABFile(MultipartFile file) throws IllegalStateException, IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        var fileName = StringUtils.cleanPath(file.getOriginalFilename());
        var targetLocation = fileStorageLocation.resolve(fileName);
        file.transferTo(targetLocation);

        // como o CNAB sera processado apenas uma vez. Por isso, o parametro sera o nome dele (jobParameters) que coloca o path completo do recurso
        var jobParameters = new JobParametersBuilder()
            .addJobParameter("cnab", file.getOriginalFilename(), String.class, true) // o true sinalizar que o "cnab" eh um id - logo, nao rodara novamente (assim eh o controle do unicidade)
            .addJobParameter("cnabFile", "file:" + targetLocation.toString(), String.class, false)
            .toJobParameters();
            
        jobLauncher.run(job, jobParameters);

    }
}
