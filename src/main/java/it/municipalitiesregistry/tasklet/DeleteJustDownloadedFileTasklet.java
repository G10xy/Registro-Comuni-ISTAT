package it.municipalitiesregistry.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DeleteJustDownloadedFileTasklet implements Tasklet {

    @Value("${registry.local-file-read}")
    private String localFileRead;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        deleteFileIfExists(localFileRead);
        return RepeatStatus.FINISHED;
    }

    public void deleteFileIfExists(String fileLocation) {
        var filePath = Paths.get(fileLocation);
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
