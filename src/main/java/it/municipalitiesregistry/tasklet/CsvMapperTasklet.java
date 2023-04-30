package it.municipalitiesregistry.tasklet;

import com.opencsv.bean.CsvToBeanBuilder;
import it.municipalitiesregistry.model.RegistryPlaceDTO;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvMapperTasklet implements Tasklet {

    @Value("${registry.local-file}")
    private String localFile;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws IOException {
        // Store the list in the ExecutionContext
        chunkContext.getStepContext().getStepExecution().getExecutionContext().put("newRegistry", readAllPlacesFromNewRegistry());
        return RepeatStatus.FINISHED;
    }

    private List<RegistryPlaceDTO> readAllPlacesFromNewRegistry() throws IOException {
        File file = new File(localFile);
        List<RegistryPlaceDTO> registryPlaces = new CsvToBeanBuilder(new FileReader(file, StandardCharsets.ISO_8859_1)).withSeparator(';').withType(RegistryPlaceDTO.class).build().parse();
        //Remove first line with columns names
        registryPlaces.remove(0);
        return registryPlaces;
    }
}
