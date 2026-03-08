package it.municipalitiesregistry.tasklet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DownloadFileTaskletTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Download file from URI and save it locally with correct content")
    void shouldDownloadFileSuccessfully() throws Exception {
        // Create a local file to serve as source via file:// URI
        Path sourceFile = tempDir.resolve("source.csv");
        Files.writeString(sourceFile, "header1;header2\nvalue1;value2");

        Path targetFile = tempDir.resolve("downloaded.csv");

        DownloadFileTasklet tasklet = new DownloadFileTasklet();
        ReflectionTestUtils.setField(tasklet, "permalink", sourceFile.toUri().toString());
        ReflectionTestUtils.setField(tasklet, "localFile", targetFile.toString());

        StepContribution stepContribution = mock(StepContribution.class);
        ChunkContext chunkContext = mock(ChunkContext.class);

        RepeatStatus status = tasklet.execute(stepContribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, status);
        assertTrue(Files.exists(targetFile));
        assertEquals("header1;header2\nvalue1;value2", Files.readString(targetFile));
    }

    @Test
    @DisplayName("Throw RuntimeException when download URL is unreachable")
    void shouldThrowOnInvalidUrl() {
        DownloadFileTasklet tasklet = new DownloadFileTasklet();
        ReflectionTestUtils.setField(tasklet, "permalink", "http://invalid.nonexistent.host/file.csv");
        ReflectionTestUtils.setField(tasklet, "localFile", tempDir.resolve("output.csv").toString());

        StepContribution stepContribution = mock(StepContribution.class);
        ChunkContext chunkContext = mock(ChunkContext.class);

        assertThrows(RuntimeException.class, () -> tasklet.execute(stepContribution, chunkContext));
    }

    @Test
    @DisplayName("Overwrite existing local file when downloading again")
    void shouldOverwriteExistingFile() throws Exception {
        Path sourceFile = tempDir.resolve("source.csv");
        Files.writeString(sourceFile, "new content");

        Path targetFile = tempDir.resolve("existing.csv");
        Files.writeString(targetFile, "old content");

        DownloadFileTasklet tasklet = new DownloadFileTasklet();
        ReflectionTestUtils.setField(tasklet, "permalink", sourceFile.toUri().toString());
        ReflectionTestUtils.setField(tasklet, "localFile", targetFile.toString());

        StepContribution stepContribution = mock(StepContribution.class);
        ChunkContext chunkContext = mock(ChunkContext.class);

        tasklet.execute(stepContribution, chunkContext);

        assertEquals("new content", Files.readString(targetFile));
    }
}
