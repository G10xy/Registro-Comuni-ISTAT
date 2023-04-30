package it.municipalitiesregistry.config;


import it.municipalitiesregistry.decider.FilesDifferenceDecider;
import it.municipalitiesregistry.decider.FilesExistingDecider;
import it.municipalitiesregistry.listener.UpdateRegistryStepExecutionListener;
import it.municipalitiesregistry.tasklet.CsvMapperTasklet;
import it.municipalitiesregistry.tasklet.DeleteJustDownloadedFileTasklet;
import it.municipalitiesregistry.tasklet.DownloadFileTasklet;
import it.municipalitiesregistry.tasklet.UpdateRegistryTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchUpdateRegistryPlaceConfig {

    private final DownloadFileTasklet downloadFileTasklet;
    private final FilesDifferenceDecider filesDifferenceDecider;
    private final FilesExistingDecider filesExistingDecider;
    private final DeleteJustDownloadedFileTasklet deleteJustDownloadedFileTasklet;
    private final CsvMapperTasklet csvMapperTasklet;
    private final UpdateRegistryTasklet updateRegistryTasklet;
    private final UpdateRegistryStepExecutionListener updateRegistryStepExecutionListener;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public Job registryTowns(JobRepository jobRepository) {
        return new JobBuilder("myJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(downloadFileStep(jobRepository))
                .next(filesExistingDecider).on("FAIL").fail()
                .from(filesExistingDecider).on("OK")
                .to(filesDifferenceDecider).on("CONTINUE").to(updateRegistryStep(jobRepository))
                .from(filesDifferenceDecider).on("SAME").to(deleteJustDownloadedFileStep(jobRepository))
                .end()
                .build();
    }

    @Bean
    public Step downloadFileStep(JobRepository jobRepository) {
        return new StepBuilder("downloadFileStep", jobRepository)
                .tasklet(downloadFileTasklet, transactionManager())
                .build();
    }

    @Bean
    public Step deleteJustDownloadedFileStep(JobRepository jobRepository) {
        return new StepBuilder("deleteJustDownloadedFileStep", jobRepository)
                .tasklet(deleteJustDownloadedFileTasklet, transactionManager())
                .build();
    }

    @Bean
    public Step updateRegistryStep(JobRepository jobRepository) {
        return new StepBuilder("updateRegistryStep", jobRepository)
                .tasklet(updateRegistryTasklet, transactionManager())
                .tasklet(csvMapperTasklet, transactionManager())
                .listener(updateRegistryStepExecutionListener)
                .build();
    }
}
