package it.municipalitiesregistry.service;

import it.municipalitiesregistry.mapper.RegistryPlaceMapper;
import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistryPlaceBatchService {

    private static final int BATCH_SIZE = 500;

    private final RegistryPlaceRepository registryPlaceRepository;
    private final RegistryPlaceMapper mapper;

    @Transactional
    public void saveOrUpdateAll(List<RegistryPlaceCsvDTO> places, LocalDateTime dateTime) {
        List<RegistryPlaceEntity> batch = new ArrayList<>(BATCH_SIZE);
        for (var place : places) {
            batch.add(prepareEntity(place, dateTime));
            if (batch.size() >= BATCH_SIZE) {
                registryPlaceRepository.saveAll(batch);
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            registryPlaceRepository.saveAll(batch);
        }
        log.info("Saved/updated {} municipalities", places.size());
    }

    @Transactional
    public void updatePastOnes(LocalDateTime dateTime) {
        registryPlaceRepository.updatePastOnes(dateTime);
    }

    private RegistryPlaceEntity prepareEntity(RegistryPlaceCsvDTO place, LocalDateTime dateTime) {
        Optional<RegistryPlaceEntity> currentEntity = registryPlaceRepository.findByCompositeId(
                place.getCodiceCatastaleDelComune(),
                place.getDenominazioneInItaliano(),
                place.getDenominazioneUnitaTerritorialeSovracomunale(),
                place.getDenominazioneRegione());
        RegistryPlaceEntity entity;
        if (currentEntity.isPresent()) {
            entity = currentEntity.get();
            mapper.mapDtoToEntity(place, entity);
        } else {
            entity = mapper.dtoCsvToEntity(place);
            entity.setValidFrom(dateTime);
        }
        entity.setLastUpdate(dateTime);
        return entity;
    }

}
