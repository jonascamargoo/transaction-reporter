package com.example.reporter.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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

import com.example.reporter.exceptions.customExceptions.FileTransferIOException;
import com.example.reporter.exceptions.customExceptions.FileTransferStateException;
import com.example.reporter.exceptions.customExceptions.JAlreadyRunningException;
import com.example.reporter.exceptions.customExceptions.JInstanceAlreadyCompleteException;
import com.example.reporter.exceptions.customExceptions.JParametersInvalidException;
import com.example.reporter.exceptions.customExceptions.JRestartException;

@Service
public class CNABFileProcessingService {
    private final Path fileStorageLocation;

    // Since the job is annotated as a Bean in the configuration, Spring
    // automatically runs the job upon initialization. Therefore, I will disable
    // this behavior in application.properties. Later, I will signal it with
    // @Qualifier("jobLauncherAsync")

    // async
    private final JobLauncher jobLauncher;

    // sync
    private final Job job;

    public CNABFileProcessingService(
            @Value("${file.upload-dir}") String fileUploadDir,
            @Qualifier("jobLauncherAsync") JobLauncher jobLauncher,
            Job job) {
        this.fileStorageLocation = Paths.get(fileUploadDir);
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    
    // uploadCNABFile make the file upload and transfer to "tmp" folder, then return its Path
    public Path uploadCNABFile(MultipartFile file) {
        // file = "special%characters.txt" -> "specialcharacters.txt"
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // get final destination
        Path targetLocation = fileStorageLocation.resolve(fileName);
        try {
            file.transferTo(targetLocation);
        } catch (IllegalStateException stateException) {
            throw new FileTransferStateException();
        } catch (IOException ioExeException) {
            throw new FileTransferIOException();
        }
        return targetLocation;
    }

    
    public JobParameters createJobParameters(MultipartFile file, Path targetLocation) {   
            JobParameters jobParameters = new JobParametersBuilder()
                // Since the CNAB will be processed only once, the parameter will be its name - "cnab". The 'true' signals that "CNAB" is an identifier, ensuring uniqueness control.
                // This guarantees that the job is processed only once
                .addJobParameter("cnab", file.getOriginalFilename(), String.class, true)
                // The second parameter is the file path, therefore, it will vary (unlike the name). Hence, it will not be chosen for uniqueness control - identifying: false
                .addJobParameter("cnabFile", "file:" + targetLocation.toString(), String.class, false)
                .toJobParameters();
            return jobParameters;
    }

    
    public void executeJob(MultipartFile file) {
        try {
            Path targetLocation = uploadCNABFile(file);
            // After being correctly parameterized, the job is executed
            JobParameters parameters = createJobParameters(file, targetLocation);
            jobLauncher.run(job, parameters);
        } catch (JobExecutionAlreadyRunningException alreadyRunningException) {
            throw new JAlreadyRunningException();
        } catch (JobRestartException restartException) {
            throw new JRestartException();
        } catch (JobInstanceAlreadyCompleteException alreadyCompleteException) {
            throw new JInstanceAlreadyCompleteException();
        } catch (JobParametersInvalidException invalidParametersException) {
            throw new JParametersInvalidException(); 
        }
    }

}