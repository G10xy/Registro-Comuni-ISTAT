package it.municipalitiesregistry.tasklet;

import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.service.RegistryPlaceService;
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

    private final RegistryPlaceService registryPlaceService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        List<RegistryPlaceDTO> places = (List<RegistryPlaceDTO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("newRegistry");
        for(var place : places) {
            registryPlaceService.saveOrUpdate(place);
        }
        registryPlaceService.updatePastOnes(LocalDate.now().atStartOfDay());
        return RepeatStatus.FINISHED;
    }

}
