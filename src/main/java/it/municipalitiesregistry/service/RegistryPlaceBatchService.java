package it.municipalitiesregistry.service;

import it.municipalitiesregistry.mapper.RegistryPlaceMapper;
import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistryPlaceBatchService {

    private final RegistryPlaceRepository registryPlaceRepository;
    private final RegistryPlaceMapper mapper;

    @Transactional
    public void saveOrUpdate(RegistryPlaceCsvDTO place, LocalDateTime dateTime) {
        Optional<RegistryPlaceEntity> currentEntity = registryPlaceRepository.findByCompositeId(place.getCodiceCatastaleDelComune(), place.getDenominazioneInItaliano(), place.getDenominazioneUnitaTerritorialeSovracomunale(), place.getDenominazioneRegione());
        RegistryPlaceEntity entity;
        if (currentEntity.isPresent()) {
            entity = currentEntity.get();
            mapper.mapDtoToEntity(place, entity);
        } else {
            entity = createNewEntity(place);
            entity.setValidFrom(dateTime);
        }
        entity.setLastUpdate(dateTime);
        registryPlaceRepository.save(entity);
    }

    @Transactional
    public void updatePastOnes(LocalDateTime dateTime) {
        registryPlaceRepository.updatePastOnes(dateTime);
    }

    private RegistryPlaceEntity createNewEntity(RegistryPlaceCsvDTO place) {
        return mapper.dtoCsvToEntity(place);
    }

}
