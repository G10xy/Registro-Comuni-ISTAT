package it.municipalitiesregistry.decider;

import it.municipalitiesregistry.util.FileUtility;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FilesDifferenceDecider  implements JobExecutionDecider {

    @Value("${registry.local-file}")
    private String localFile;
    @Value("${registry.local-file-read}")
    private String localFileRead;

    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String status;
        try {
            if (checkFilesAreEquals(localFile, localFileRead)) {
                status = "SAME";
            }
            else {
                status = "CONTINUE";
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
        return new FlowExecutionStatus(status);
    }

    private boolean checkFilesAreEquals(String firstFile, String secondFile) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        if(!FileUtility.checkFileExists(secondFile)) {
            return false;
        }
        return checksum(firstFile, md).equals(checksum(secondFile, md));
    }

    public static String checksum(String filepath, MessageDigest md) throws IOException {
        try (InputStream fis = new FileInputStream(filepath)) {
            byte[] buffer = new byte[1024];
            int nread;
            while ((nread = fis.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
        }

        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
