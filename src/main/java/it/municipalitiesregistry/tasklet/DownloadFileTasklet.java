package it.municipalitiesregistry.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


@Component
public class DownloadFileTasklet implements Tasklet {

    @Value("${registry.permalink}")
    private String permalink;
    @Value("${registry.local-file}")
    private String localFile;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        try {
            downloadFile(permalink, localFile);
        } catch (IOException e) {
            throw new RuntimeException("Error downloading file", e);
        }
        return RepeatStatus.FINISHED;
    }

    private void downloadFile(String urlStr, String localFile) throws IOException {
        try (ReadableByteChannel rbc = Channels.newChannel(URI.create(urlStr).toURL().openStream());
             FileOutputStream fos = new FileOutputStream(localFile)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}
