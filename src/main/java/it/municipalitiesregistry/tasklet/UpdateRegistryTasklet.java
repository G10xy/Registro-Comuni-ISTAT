package it.municipalitiesregistry.tasklet;

import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.service.RegistryPlaceBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateRegistryTasklet  implements Tasklet {

    private final RegistryPlaceBatchService registryPlaceBatchService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        List<RegistryPlaceCsvDTO> places = (List<RegistryPlaceCsvDTO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("newRegistry");
        for(var place : places) {
            registryPlaceBatchService.saveOrUpdate(place);
        }
        registryPlaceBatchService.updatePastOnes(LocalDate.now().atStartOfDay());
        return RepeatStatus.FINISHED;
    }

}
