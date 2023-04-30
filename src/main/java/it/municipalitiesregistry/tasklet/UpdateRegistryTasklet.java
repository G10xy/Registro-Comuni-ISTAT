package it.municipalitiesregistry.tasklet;

import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceId;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateRegistryTasklet  implements Tasklet {

    private final RegistryPlaceRepository registryPlaceRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        List<RegistryPlaceDTO> places = (List<RegistryPlaceDTO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("newRegistry");
        for(var place : places) {
            saveOrUpdate(place);
        }
        registryPlaceRepository.updatePastOnes(LocalDate.now().atStartOfDay());
        return RepeatStatus.FINISHED;
    }

    private void saveOrUpdate(RegistryPlaceDTO place) {
        Optional<RegistryPlaceEntity> currentEntity = registryPlaceRepository.findByIdCodiceCatastaleDelComuneAndIdDenominazioneInItalianoAndIdDenominazioneUnitaTerritorialeSovracomunaleAndIdDenominazioneRegione(
                place.getCodiceCatastaleDelComune(),
                place.getDenominazioneInItaliano(),
                place.getDenominazioneUnitaTerritorialeSovracomunale(),
                place.getDenominazioneRegione());
        if (currentEntity.isPresent()) {
            var entity = currentEntity.get();
            entity.setLastUpdate(LocalDateTime.now());
            registryPlaceRepository.save(entity);
        } else {
            var newEntity = fromRegistryPlaceCsvToEntity(place);
            registryPlaceRepository.save(newEntity);
        }
    }

    private RegistryPlaceEntity fromRegistryPlaceCsvToEntity(RegistryPlaceDTO place) {
        RegistryPlaceEntity registryPlaceEntity = new RegistryPlaceEntity();
        registryPlaceEntity.setId(new RegistryPlaceId(place.getDenominazioneRegione(), place.getCodiceCatastaleDelComune(), place.getDenominazioneInItaliano(), place.getDenominazioneUnitaTerritorialeSovracomunale()));
        registryPlaceEntity.setCodiceRegione( place.getCodiceRegione() );
        registryPlaceEntity.setCodiceUniteTerritorialeSovracomunale( place.getCodiceUniteTerritorialeSovracomunale() );
        registryPlaceEntity.setCodiceProvinciaStorico( place.getCodiceProvinciaStorico() );
        registryPlaceEntity.setProgressivoDelComune( place.getProgressivoDelComune() );
        registryPlaceEntity.setCodiceComuneFormatoAlfanumerico( place.getCodiceComuneFormatoAlfanumerico() );
        registryPlaceEntity.setDenominazioneItalianaStraniera( place.getDenominazioneItalianaStraniera() );
        registryPlaceEntity.setDenominazioneAltraLingua( place.getDenominazioneAltraLingua() );
        registryPlaceEntity.setCodiceRipartizioneGeografica( place.getCodiceRipartizioneGeografica() );
        registryPlaceEntity.setRipartizioneGeografica( place.getRipartizioneGeografica() );
        registryPlaceEntity.setTipologiaUnitaTerritorialeSovracomunale( place.getTipologiaUnitaTerritorialeSovracomunale() );
        registryPlaceEntity.setFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio( place.getFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio() );
        registryPlaceEntity.setSiglaAutomobilistica( place.getSiglaAutomobilistica() );
        registryPlaceEntity.setCodiceComuneFormatoNumerico( place.getCodiceComuneFormatoNumerico() );
        registryPlaceEntity.setCodiceComuneNumericoCon110ProvinceDal2010Al2016( place.getCodiceComuneNumericoCon110ProvinceDal2010Al2016() );
        registryPlaceEntity.setCodiceComuneNumericoCon107ProvinceDal2006Al2009( place.getCodiceComuneNumericoCon107ProvinceDal2006Al2009() );
        registryPlaceEntity.setCodiceComuneNumericoCon103ProvinceDal1995Al2005( place.getCodiceComuneNumericoCon103ProvinceDal1995Al2005() );
        registryPlaceEntity.setCodiceNUTS12010( place.getCodiceNUTS12010() );
        registryPlaceEntity.setCodiceNUTS22010( place.getCodiceNUTS22010() );
        registryPlaceEntity.setCodiceNUTS32010( place.getCodiceNUTS32010() );
        registryPlaceEntity.setCodiceNUTS12021( place.getCodiceNUTS12021() );
        registryPlaceEntity.setCodiceNUTS22021( place.getCodiceNUTS22021() );
        registryPlaceEntity.setCodiceNUTS32021( place.getCodiceNUTS32021() );
        return registryPlaceEntity;
    }
}
