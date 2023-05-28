package it.municipalitiesregistry.service;

import it.municipalitiesregistry.mapper.RegistryPlaceMapper;
import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistryPlaceQueryService {

    private final RegistryPlaceRepository registryPlaceRepository;
    private final RegistryPlaceMapper mapper;

    public RegistryPlaceEntity findEntityByMunicipalCode(String codiceCatastaleDelComune) {
        return registryPlaceRepository.findByIdCodiceCatastaleDelComuneAndCurrentValidTrue(codiceCatastaleDelComune).orElseThrow();
    }

    public RegistryPlaceDTO findByCadastralCode(String codiceCatastaleDelComune) {
        return mapper.fromEntityToResponseDto(findEntityByMunicipalCode(codiceCatastaleDelComune));
    }

    public List<String> getAllRegions() {
        return registryPlaceRepository.findDistinctDenominazioneRegione();
    }

    public List<String> getAllProvincesByRegion(String region) {
        return registryPlaceRepository.findDistinctDenominazioneUnitaTerritorialeSovracomunale(region);
    }

    public List<RegistryPlaceDTO> getAllCitiesByProvinceAndRegion(String province, String region) {
        return registryPlaceRepository.findDistinctDenominazioneInItaliano(province, region).stream()
                .map(mapper::fromEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public List<RegistryPlaceDTO> getAllCitiesByProvince(String province) {
        return registryPlaceRepository.findDistinctDenominazioneInItalianoByProvince(province).stream()
                .map(mapper::fromEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public List<RegistryPlaceDTO> getAllCitiesByRegion(String region) {
        return registryPlaceRepository.findDistinctDenominazioneInItalianoByRegion(region).stream()
                .map(mapper::fromEntityToResponseDto)
                .collect(Collectors.toList());
    }
}
