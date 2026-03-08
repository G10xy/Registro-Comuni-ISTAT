package it.municipalitiesregistry.tasklet;

import it.municipalitiesregistry.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DeleteJustDownloadedFileTasklet implements Tasklet {

    @Value("${registry.local-file}")
    private String downloadedFile;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        try {
            Utility.deleteFileIfExists(downloadedFile);
        } catch (IOException e) {
            log.error("Error deleting downloaded file: {}", downloadedFile, e);
        }
        return RepeatStatus.FINISHED;
    }
}
