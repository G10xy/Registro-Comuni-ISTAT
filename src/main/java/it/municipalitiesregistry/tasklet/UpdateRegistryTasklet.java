package it.municipalitiesregistry.tasklet;

import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.service.RegistryPlaceBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateRegistryTasklet  implements Tasklet {

    private final RegistryPlaceBatchService registryPlaceBatchService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        log.info("Inizio batch aggiornamento comuni");
        LocalDateTime now = LocalDateTime.now();
        List<RegistryPlaceCsvDTO> places = (List<RegistryPlaceCsvDTO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("newRegistry");
        for (var place : places) {
            registryPlaceBatchService.saveOrUpdate(place, now);
        }
        registryPlaceBatchService.updatePastOnes(now.minusMinutes(1));
        log.info("Fine batch aggiornamento comuni");
        return RepeatStatus.FINISHED;
    }

}
