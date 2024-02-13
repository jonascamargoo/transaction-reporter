package com.example.reporter.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
public class FileProcessingService {
    private final Path fileStorageLocation;

    // Como o Job foi anotado como Bean, o spring ira executa-lo automaticamente durante a inicializacao. Dito isso, esse comportamento sera desabilitado em application.properties, para entao sinaliza-lo com @Qualifier("jobLauncherAsync") - buscando um processamento async

    // async
    private final JobLauncher jobLauncher;

    // sync
    private final Job job;

    public FileProcessingService(
            @Value("${file.upload-dir}") String fileUploadDir,
            @Qualifier("jobLauncherAsync") JobLauncher jobLauncher,
            Job job) {
        this.fileStorageLocation = Paths.get(fileUploadDir);
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    // Metodos uploadCNABFile e uploadCNABFiles, para lidar com um ou mais arquivos,
    // respecivamente. Ao passo que mantem a logica especifica do uploadFileInternal
    // encapsulada

    public Path uploadCNABFile(MultipartFile file) {
        return uploadFileInternal(file);
    }

    public List<Path> uploadCNABFiles(List<MultipartFile> files) {
        List<Path> targetLocations = new ArrayList<>();
        for (MultipartFile file : files) {
            targetLocations.add(uploadFileInternal(file));
        }
        return targetLocations;
    }

    private Path uploadFileInternal(MultipartFile file) {
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

    // Metodo para criacao de parametros para o Job

    // primeiramente, adiciona-se um parametro para controle de unicidade
    // (especificado na documentacao), o qual sera o proprio nome do arquivo. O
    // identifier setado como true, garante esse controle

    // O segundo parametro sera o path do arquivo. Diferente do nome do arquivo, o
    // caminho pode variar, ou seja, se mudarmos o arquivo ja processado anteriormente, ele poderia ser processado de novo. Portanto, nao sera utilizado para controle de unicidade
    // - identifier false

    public JobParameters createJobParameters(MultipartFile file, Path targetLocation) {
        return new JobParametersBuilder()
                .addJobParameter("cnab", file.getOriginalFilename(), String.class, true)
                .addJobParameter("cnabFile", "file:" + targetLocation.toString(), String.class, false)
                .toJobParameters();
    }

    // Metodos para processamento de um ou mais arquivos. Mesma estrategia de
    // encapsulamento do uploadFileInternal repetida para o processFileInternal

    // Em processFileInternal, tenta-se parametrizar o Job corretamente. Apos a
    // parametrizacao, o Job eh executado em jobLauncher.run(job, parameters)

    public void processFile(MultipartFile file) {
        Path targetLocation = uploadFileInternal(file);
        processFileInternal(file, targetLocation);
    }

    public void processFiles(List<MultipartFile> files) {
        List<Path> targetLocations = uploadCNABFiles(files);
        for (int i = 0; i < files.size() - 1; i++) {
            processFileInternal(files.get(i), targetLocations.get(i));
        }
    }

    private void processFileInternal(MultipartFile file, Path targetLocation) {
        try {
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