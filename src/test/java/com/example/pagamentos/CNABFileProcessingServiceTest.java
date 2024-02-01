package com.example.pagamentos;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

import com.example.reporter.services.CNABFileProcessingService;

@ExtendWith(MockitoExtension.class)
public class CNABFileProcessingServiceTest {
    @InjectMocks
    private CNABFileProcessingService cnabFileProcessingService;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job job;

    


}
