package it.municipalitiesregistry.api;

import graphql.kickstart.tools.GraphQLQueryResolver;
import it.municipalitiesregistry.mapper.RegistryPlaceMapper;
import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final RegistryPlaceRepository registryPlaceRepository;
    private final RegistryPlaceMapper mapper;

    public RegistryPlaceEntity findEntityByMunicipalCode(String codiceCatastaleDelComune) {
        return registryPlaceRepository.findByIdCodiceCatastaleDelComuneAndCurrentValidTrue(codiceCatastaleDelComune).orElseThrow();
    }

    public RegistryPlaceDTO municipalityByCadastralCode(String codiceCatastaleDelComune) {
        return mapper.fromEntityToResponseDto(findEntityByMunicipalCode(codiceCatastaleDelComune));
    }
}
