package it.municipalitiesregistry.service;

import it.municipalitiesregistry.mapper.RegistryPlaceMapper;
import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistryPlaceQueryService {

    private final RegistryPlaceRepository registryPlaceRepository;
    private final RegistryPlaceMapper mapper;

    public RegistryPlaceEntity findEntityByMunicipalCode(String codiceCatastaleDelComune) {
        return registryPlaceRepository.findByIdCodiceCatastaleDelComune(codiceCatastaleDelComune).orElseThrow();
    }

    public RegistryPlaceDTO findByMunicipalCode(String codiceCatastaleDelComune) {
        return mapper.fromEntityToResponseDto(findEntityByMunicipalCode(codiceCatastaleDelComune));
    }
}
