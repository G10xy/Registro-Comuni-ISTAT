package it.municipalitiesregistry.listener;

import it.municipalitiesregistry.util.FileUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@Component
public class UpdateRegistryStepExecutionListener implements StepExecutionListener {

    @Value("${registry.local-file-read-name}")
    private String localFileReadName;
    @Value("${registry.local-file}")
    private String localFile;
    @Value("${registry.local-file-read}")
    private String localFileRead;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            FileUtility.deleteFileIfExists(localFileRead);
            FileUtility.renameFile(Paths.get(localFile), localFileReadName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Step " + stepExecution.getStepName() + " has finished");
        return stepExecution.getExitStatus();
    }
}
