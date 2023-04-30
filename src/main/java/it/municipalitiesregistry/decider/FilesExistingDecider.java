package it.municipalitiesregistry.decider;

import it.municipalitiesregistry.util.FileUtility;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilesExistingDecider implements JobExecutionDecider {

    @Value("${registry.local-file}")
    private String localFile;


    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String status;
        if (FileUtility.checkFileExists(localFile)) {
            status = "OK";
        }
        else {
            status = "FAIL";
        }
        return new FlowExecutionStatus(status);
    }



}
