package it.municipalitiesregistry;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
public class MunicipalityBatchJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job importMunicipalitiesJob;

    @Test
    public void testImportMunicipalitiesJobExecution() throws Exception {
        // Atribui o job ao utilitário de teste
        jobLauncherTestUtils.setJob(importMunicipalitiesJob);

        // Executa o Job de Spring Batch
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Valida se o batch terminou com sucesso (COMPLETED)
        assertThat(jobExecution.getStatus().toString()).isEqualTo("COMPLETED");
    }
}